package com.UBERAPP.UBER_BACKEND_PROJECT.services;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String body);

    void sendEmail(String[] toEmail, String subject, String body);
}
