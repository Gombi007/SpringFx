package com.fx.springfx.services;

import com.fx.springfx.entities.Book;

import java.util.zip.DataFormatException;


public class DataValidation {

    private static void checkEmptyFields(String isbn10, String title, String author, String year, String pages) throws
            Exception {
        String exceptionMessage = "";
        if (isbn10.isEmpty()) {
            exceptionMessage += " Missing ISBN-10 ;";
        }
        if (author.isEmpty()) {
            exceptionMessage += " Missing Author ;";
        }
        if (title.isEmpty()) {
            exceptionMessage += " Missing Title ;";
        }
        if (year.isEmpty()) {
            exceptionMessage += " Missing Year ;";
        }
        if (pages.isEmpty()) {
            exceptionMessage += " Missing Pages ;";
        }
        if (!exceptionMessage.isEmpty()) {
            throw new DataFormatException(exceptionMessage);
        }

    }

    protected static Book checkFieldData(String id, String isbn10, String title, String author, String year, String pages) throws
            Exception {

        checkEmptyFields(isbn10, title, author, year, pages);
        Long longId = 0L;
        Long longIsbn10 = null;
        Integer integerYear = null;
        Integer integerPages = null;
        String exceptionMessage = "";
        if (!id.isEmpty()) {
            try {
                longId = Long.parseLong(id);
            } catch (NumberFormatException exception) {
                exceptionMessage += "ID field contains numbers only.;";
            }
        }
        try {
            longIsbn10 = Long.parseLong(isbn10);
        } catch (NumberFormatException exception) {
            exceptionMessage += "ISBN-10 field contains numbers only.;";
        }
        try {
            integerYear = Integer.parseInt(year);
        } catch (NumberFormatException exception) {
            exceptionMessage += "Publish year field contains numbers only.;";
        }
        try {
            integerPages = Integer.parseInt(pages);
        } catch (NumberFormatException exception) {
            exceptionMessage += "Pages field contains numbers only.;";
        }
        if (!exceptionMessage.isEmpty()) {
            throw new DataFormatException(exceptionMessage);
        }
        if (longId > 0) {
            return new Book(longId, longIsbn10, title, author, integerYear, integerPages);
        }
        return new Book(longIsbn10, title, author, integerYear, integerPages);
    }
}
