/*
Class Description: Extends exception to handle bad config format
Authors: Tanner Bearden and Brayden Clark
Date: 2/27/2024
Collaborators: n/a
Sources: n/a
 */
package clueGame;

public class BadConfigFormatException extends Exception {

        /**
         * default constructor
         */
        public BadConfigFormatException() {
            super("Bad Config Format");
        }
        /**
         * 
         * @param message
         */
        public BadConfigFormatException(String message) {
            super(message);
        }
}
