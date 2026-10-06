-- hotel_booking_test

CREATE TABLE hotels (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        city VARCHAR(255) NOT NULL
);

CREATE TABLE rooms (
                       id BIGSERIAL PRIMARY KEY,
                       hotel_id BIGINT NOT NULL,
                       room_number VARCHAR(50) NOT NULL,
                       capacity INTEGER NOT NULL,
                       price_per_night NUMERIC(10, 2) NOT NULL,

                       CONSTRAINT fk_rooms_hotel
                           FOREIGN KEY (hotel_id)
                               REFERENCES hotels(id)
);

CREATE TABLE bookings (
                          id BIGSERIAL PRIMARY KEY,
                          room_id BIGINT NOT NULL,
                          guest_name VARCHAR(255) NOT NULL,
                          check_in DATE NOT NULL,
                          check_out DATE NOT NULL,

                          CONSTRAINT fk_bookings_room
                              FOREIGN KEY (room_id)
                                  REFERENCES rooms(id)
);