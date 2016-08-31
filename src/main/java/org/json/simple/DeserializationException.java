/* See: README for this file's copyright, terms, and conditions. */
package org.json.simple;

/** DeserializationException explains why and where the exception occurs in source JSON text.
 * 
 * @since 2.0.0 */
public class DeserializationException extends Exception{
    /** The kinds of exceptions that can trigger a DeserializationException. */
    enum Problems{
        @SuppressWarnings("javadoc")
        DISALLOWED_TOKEN,
        @SuppressWarnings("javadoc")
        UNEXPECTED_CHARACTER,
        @SuppressWarnings("javadoc")
        UNEXPECTED_EXCEPTION,
        @SuppressWarnings("javadoc")
        UNEXPECTED_TOKEN;
    }

    private static final long serialVersionUID = -7880698968187728547L;
    private final Problems      problemType;
    private final int         position;
    private final Object      unexpectedObject;

    /** @param position where the exception occurred.
     * @param problemType how the exception occurred.
     * @param unexpectedObject what caused the exception. */
    public DeserializationException(final int position, final Problems problemType, final Object unexpectedObject){
        this.position = position;
        this.problemType = problemType;
        this.unexpectedObject = unexpectedObject;
    }

    /** @return the enumeration for how the exception occurred. */
    public Problems getProblemType(){
        return this.problemType;
    }

    @Override
    public String getMessage(){
        final StringBuilder sb = new StringBuilder();
        switch(this.problemType){
            case DISALLOWED_TOKEN:
                sb.append("The disallowed token (").append(this.unexpectedObject).append(") was found at position ").append(this.position).append(". If this is in error, try again with a parse that allows the token instead. Otherwise, fix the parsable string and try again.");
                break;
            case UNEXPECTED_CHARACTER:
                sb.append("The unexpected character (").append(this.unexpectedObject).append(") was found at position ").append(this.position).append(". Fix the parsable string and try again.");
                break;
            case UNEXPECTED_TOKEN:
                sb.append("The unexpected token ").append(this.unexpectedObject).append(" was found at position ").append(this.position).append(". Fix the parsable string and try again.");
                break;
            case UNEXPECTED_EXCEPTION:
                sb.append("Please report this to the library's maintainer. The unexpected exception that should be addressed before trying again occurred at position ").append(this.position).append(": ").append(this.unexpectedObject);
                break;
            default:
                sb.append("Please report this to the library's maintainer. An error at position ").append(this.position).append(" occurred. There are no recovery recommendations available.");
                break;
        }
        return sb.toString();
    }

    /** @return an index of the string character the error type occurred at. */
    public int getPosition(){
        return this.position;
    }

    /** @return a representation of what caused the exception. */
    public Object getUnexpectedObject(){
        return this.unexpectedObject;
    }
}
