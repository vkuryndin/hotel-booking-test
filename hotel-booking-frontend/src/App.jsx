import { useEffect, useState } from 'react'
import './App.css'

function App() {
    const [hotels, setHotels] = useState([])
    const [selectedHotelId, setSelectedHotelId] = useState(null)
    const [rooms, setRooms] = useState([])
    const [selectedRoomId, setSelectedRoomId] = useState(null)
    const [guestName, setGuestName] = useState('')
    const [checkIn, setCheckIn] = useState('')
    const [checkOut, setCheckOut] = useState('')
    const [message, setMessage] = useState('')
    const [bookings, setBookings] = useState([])
    const [searchCheckIn, setSearchCheckIn] = useState('')
    const [searchCheckOut, setSearchCheckOut] = useState('')

    useEffect(() => {
        fetch('http://localhost:8080/hotels')
            .then(response => response.json())
            .then(data => setHotels(data))
        loadBookings()
    }, [])

    function selectHotel(hotelId) {
        setMessage('')
        setSelectedHotelId(hotelId)

        fetch(`http://localhost:8080/hotels/${hotelId}/rooms`)
            .then(response => response.json())
            .then(data => setRooms(data))
    }

    function createBooking() {
        setMessage('')

        if (!guestName || !checkIn || !checkOut) {
            setMessage('Please fill in all fields')
            return
        }

        if (checkOut <= checkIn) {
            setMessage('Check-out date must be after check-in date')
            return
        }
        fetch('http://localhost:8080/bookings', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                roomId: selectedRoomId,
                guestName: guestName,
                checkIn: checkIn,
                checkOut: checkOut
            })
        })
            .then(response => {
                if (response.status === 409) {
                    throw new Error('Room is already booked for these dates')
                }

                if (!response.ok) {
                    throw new Error('Booking failed')
                }

                return response.json()
            })
            .then(data => {
                setMessage(`Booking created successfully. ID: ${data.id}`)
                setGuestName('')
                setCheckIn('')
                setCheckOut('')
                setSelectedRoomId(null)
                loadBookings()
            })
            .catch(error => {
                setMessage(error.message)
            })
    }
    function loadBookings() {
        fetch('http://localhost:8080/bookings')
            .then(response => response.json())
            .then(data => setBookings(data))
    }

    function cancelBooking(id) {
        const confirmed = window.confirm('Cancel this booking?')

        if (!confirmed) {
            return
        }
        fetch(`http://localhost:8080/bookings/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to cancel booking')
                }

                loadBookings()
            })
            .catch(error => {
                setMessage(error.message)
            })
    }
    function loadAvailableRooms() {
        if (!selectedHotelId || !searchCheckIn || !searchCheckOut) {
            setMessage('Select hotel and dates')
            return
        }

        fetch(
            `http://localhost:8080/hotels/${selectedHotelId}/rooms/available?checkIn=${searchCheckIn}&checkOut=${searchCheckOut}`
        )
            .then(response => response.json())
            .then(data => setRooms(data))
    }

    return (
        <div className="container">
            <h1>Hotel Booking</h1>

            <div className="main-layout">

                {/* LEFT */}
                <div className="left-column">
                    <h2>Hotels</h2>

                    <div className="hotel-list">
                        {hotels.map(hotel => (
                            <div
                                className={`hotel-card ${selectedHotelId === hotel.id ? 'selected' : ''}`}
                                key={hotel.id}
                                onClick={() => selectHotel(hotel.id)}
                            >
                                <h3>{hotel.name}</h3>
                                <p>{hotel.city}</p>
                            </div>
                        ))}
                    </div>
                    <div className="availability-search">
                        <div className="field-group">
                            <label>Check-in</label>
                            <input
                                type="date"
                                value={searchCheckIn}
                                onChange={e => setSearchCheckIn(e.target.value)}
                            />
                        </div>

                        <div className="field-group">
                            <label>Check-out</label>
                            <input
                                type="date"
                                value={searchCheckOut}
                                onChange={e => setSearchCheckOut(e.target.value)}
                            />
                        </div>

                        <button onClick={loadAvailableRooms}>
                            Find available
                        </button>
                    </div>

                    {selectedHotelId && (
                        <div className="rooms-section">
                            <h2>Rooms</h2>

                            <div className="room-list">
                                {rooms.map(room => (
                                    <div className="room-card" key={room.id}>
                                        <h3>Room {room.roomNumber}</h3>
                                        <p>Capacity: {room.capacity}</p>
                                        <p>Price: {room.pricePerNight}</p>

                                        <button onClick={() => {
                                            setMessage('')
                                            setSelectedRoomId(room.id)
                                        }}>
                                            Book
                                        </button>
                                    </div>
                                ))}
                            </div>
                        </div>
                    )}

                    {selectedRoomId && (
                        <div className="booking-form">
                            <h2>New booking</h2>

                            <input
                                type="text"
                                placeholder="Guest name"
                                value={guestName}
                                onChange={e => setGuestName(e.target.value)}
                            />

                            <input
                                type="date"
                                value={checkIn}
                                onChange={e => setCheckIn(e.target.value)}
                            />

                            <input
                                type="date"
                                value={checkOut}
                                onChange={e => setCheckOut(e.target.value)}
                            />

                            <button onClick={createBooking}>
                                Confirm booking
                            </button>
                        </div>
                    )}

                    {message && (
                        <p className="success-message">{message}</p>
                    )}
                </div>

                {/* RIGHT */}
                <div className="right-column">
                    <div className="bookings-header">
                        <h2>Bookings</h2>
                        <button onClick={loadBookings}>Refresh</button>
                    </div>

                    <div className="bookings-list">
                        {bookings.map(booking => (
                            <div key={booking.id} className="booking-card">
                                <strong>{booking.room.hotel.name}</strong>

                                <p>Guest: {booking.guestName}</p>
                                <p>Room: {booking.room.roomNumber}</p>
                                <p>{booking.checkIn} → {booking.checkOut}</p>
                                <button onClick={() => cancelBooking(booking.id)}>
                                    Cancel
                                </button>
                            </div>
                        ))}
                    </div>
                </div>

            </div>
        </div>
    )
}

export default App