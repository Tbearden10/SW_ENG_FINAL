package clueGame;

public class BadConfigFormatException extends Exception {
        
        public BadConfigFormatException() {
            super("Bad Config Format");
        }
        
        public BadConfigFormatException(String message) {
            super(message);
        }
}
