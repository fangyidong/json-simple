/* See: README for this file's copyright, terms, and conditions. */
package org.json.simple;

/** Represents structural entities in JSON.
 * 
 * @since 2.0.0 */
class Yytoken{
    /** Represents the different kinds of tokens. */
    enum Types{
        /** Tokens of this type will always have a value of ":" */
        COLON,
        /** Tokens of this type will always have a value of "," */
        COMMA,
        /** Tokens of this type will always have a value that is a boolean, null, number, or string. */
        DATUM,
        /** Tokens of this type will always have a value of "" */
        END,
        /** Tokens of this type will always have a value of "{" */
        LEFT_BRACE,
        /** Tokens of this type will always have a value of "[" */
        LEFT_SQUARE,
        /** Tokens of this type will always have a value of "}" */
        RIGHT_BRACE,
        /** Tokens of this type will always have a value of "]" */
        RIGHT_SQUARE;
    }

    private final Types  type;
    private final Object value;

    /** @param type represents the kind of token the instantiated token will be.
     * @param value represents the value the token is associated with, will be ignored unless type is equal to
     *        Types.DATUM.
     * @see Types */
    Yytoken(final Types type, final Object value){
        /* Sanity check. Make sure the value is ignored for the proper value unless it is a datum token. */
        switch(type){
            case COLON:
                this.value = ":";
                break;
            case COMMA:
                this.value = ",";
                break;
            case END:
                this.value = "";
                break;
            case LEFT_BRACE:
                this.value = "{";
                break;
            case LEFT_SQUARE:
                this.value = "[";
                break;
            case RIGHT_BRACE:
                this.value = "}";
                break;
            case RIGHT_SQUARE:
                this.value = "]";
                break;
            default:
                this.value = value;
                break;
        }
        this.type = type;
    }

    /** @return which of the Types the token is.
     * @see Types */
    Types getType(){
        return this.type;
    }

    /** @return what the token is.
     * @see Types */
    Object getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        final StringBuffer sb = new StringBuffer();
        sb.append(this.type.toString()).append("(").append(this.value).append(")");
        return sb.toString();
    }
}
