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
