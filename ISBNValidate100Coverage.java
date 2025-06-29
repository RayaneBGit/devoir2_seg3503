package Validator;
import static Validator.ISBNValidate.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class ISBNValidate100Coverage {

    @Test
    public void testAppendCheckDigitToISBN12_valid(){
        String isbn12 = "978030640615";
        String expected = "9780306406157";
        String actual = ISBNValidate.appendCheckDigitToISBN12(isbn12);
        assertEquals(expected, actual);
    }
    @Test(expected =
    IllegalArgumentException.class)
    public void testAppendCheckDigitToISBN12_invalidLength(){
        ISBNValidate.appendCheckDigitToISBN12("12345678901");
    }
    @Test
    public void testValidISBN9() {
        String result = appendCheckDigitToISBN9("030640615");
        assertEquals("0306406152", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidISBN9Length() {
        appendCheckDigitToISBN9("03064061");
    }
    @Test
    public void testValidISBN10() {
        String validISBN = "0306406152";
        ensureISBN10Valid(validISBN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidISBN10Length() {
        String invalidISBN = "03064061";
        ensureISBN10Valid(invalidISBN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidISBN10EndingWithLowercaseX() {
        String invalidISBN = "03064061x";
        ensureISBN10Valid(invalidISBN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCheckDigit() {
        String invalidISBN = "0306406150"; // le "check digit" est incorrect
        ensureISBN10Valid(invalidISBN);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testISBN13LengthIncorrect() {
        String invalidISBN = "03064061";
        ensureISBN13Valid(invalidISBN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testISBN13PrefixIncorrect() {
        // préfixe incorrect (ni "978" ni "979")
        String invalidISBN = "1234567890123";
        ensureISBN13Valid(invalidISBN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testISBN13InvalidCheckDigit() {
        String invalidISBN = "9790306406150";
        ensureISBN13Valid(invalidISBN);
    }

    @Test
    public void testISBN10ValidCheckDigit() {
        assertTrue(isISBN10CheckDigitValid("0306406152"));
    }


    @Test
    public void testISBN10InvalidCheckDigit() {
        assertFalse(isISBN10CheckDigitValid("0306406158"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testISBN10InvalidLength() {
        isISBN10CheckDigitValid("030640615");
    }
    @Test
    public void testISBN13ValidCheckDigit() {
        assertTrue(isISBN13CheckDigitValid("9780306406157"));
    }

    @Test
    public void testISBN13InvalidCheckDigit2() {
        assertFalse(isISBN13CheckDigitValid("9780306406158"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testISBN13InvalidLength() {
        isISBN13CheckDigitValid("978030640615");
    }
    @Test
    public void testISBN10To13Conversion() {
        assertEquals("9780306406157", isbn10To13("0306406157"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testISBN10To13InvalidLength() {
        isbn10To13("030640615");
    }
    @Test
    public void testISBN13To10_ValidConversion() {
        assertEquals("0306406152", isbn13To10("9780306406157"));
    }

    @Test
    public void testISBN13To10_NonConvertiblePrefix() {
        assertEquals("n/a", isbn13To10("9790306406157"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testISBN13To10_InvalidLength() {
        isbn13To10("978030640615");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testTidyISBN_NullInput() {
        tidyISBN10or13InsertingDashes(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTidyISBN_EmptyInput() {
        tidyISBN10or13InsertingDashes("");
    }
    @Test
    public void testTidyISBN_ValidISBN10() {
        String result = tidyISBN10or13InsertingDashes("0-306-40615-2");
        assertEquals("0-306-40615-2", result);
    }
    @Test
    public void testTidyISBN_ValidISBN13() {
        String result = tidyISBN10or13InsertingDashes("9780306406157");
        assertEquals("978-0-306-40615-7", result);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testTidyISBN_InvalidLength() {
        tidyISBN10or13InsertingDashes("123456789");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testNullInput() {
        tidyISBN10or13RemovingDashes(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyInput() {
        tidyISBN10or13RemovingDashes("");
    }

    @Test
    public void testValidISBN10_2() {
        assertEquals("0306406152", tidyISBN10or13RemovingDashes("0-306-40615-2"));
    }

    @Test
    public void testValidISBN13() {
        assertEquals("9780306406157", tidyISBN10or13RemovingDashes("978-0-306-40615-7"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidLength() {
        tidyISBN10or13RemovingDashes("12345");
    }

    //  methodes statiques privées non atteignables?


}

