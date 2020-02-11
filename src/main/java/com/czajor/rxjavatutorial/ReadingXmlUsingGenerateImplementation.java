package com.czajor.rxjavatutorial;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReadingXmlUsingGenerateImplementation {
    public static XMLStreamReader staxReader(String path) throws XMLStreamException, FileNotFoundException {
        final InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
        return XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
    }

    public static Trackpoint nextTrackpoint(XMLStreamReader reader) throws XMLStreamException {
        while(reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if (reader.getLocalName().equals("trkpt")) {
                        return parseTrackpoint(reader);
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals("gpx")) {
                        return null;
                    }
                    break;
            }
        }
        return null;
    }

    public static Trackpoint parseTrackpoint(XMLStreamReader reader) {
        return new Trackpoint(
                new BigDecimal(reader.getAttributeValue("", "lat")),
                new BigDecimal(reader.getAttributeValue("", "lon"))
        );
    }
}




