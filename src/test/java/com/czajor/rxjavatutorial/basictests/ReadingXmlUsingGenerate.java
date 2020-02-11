package com.czajor.rxjavatutorial.basictests;

import com.czajor.rxjavatutorial.Trackpoint;
import io.reactivex.Emitter;
import io.reactivex.Flowable;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static com.czajor.rxjavatutorial.ReadingXmlUsingGenerateImplementation.nextTrackpoint;
import static com.czajor.rxjavatutorial.ReadingXmlUsingGenerateImplementation.staxReader;
import static io.reactivex.Flowable.error;
import static io.reactivex.Flowable.generate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadingXmlUsingGenerate {
    private final String path = "testgps.gpx";
    private String result = "";

    @Test
    void shouldReadTrackpoints() {

        Flowable<Trackpoint> trackpoints = generate(
                () -> staxReader(path),
                this::pushNextTrackpoint,
                XMLStreamReader::close
        );

        trackpoints.subscribe(
                value -> result += ("LAT:" + value.getLat() + " LON: " + value.getLon() + "\n"),
                System.out::println
        );

        assertEquals("LAT:47.644548 LON: -122.326897\nLAT:47.644548 LON: -122.326897\nLAT:47.644548 LON: -122.326897\n", result);
    }

    void pushNextTrackpoint(XMLStreamReader reader, Emitter<Trackpoint> emitter) throws XMLStreamException {
        final Trackpoint trkpt = nextTrackpoint(reader);
        if(trkpt != null) {
            emitter.onNext(trkpt);
        } else {
            emitter.onComplete();
        }
    }

}
