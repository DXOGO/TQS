package geocoding;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import connection.ISimpleHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {


    @Mock
    ISimpleHttpClient httpClient;

    @InjectMocks
    AddressResolver resolver;

    @BeforeEach
    void setUp() { this.resolver = new AddressResolver(httpClient); }

    @AfterEach
    void cleanUp() { this.resolver = null; }
    
    @Test
    void whenResolveAlboiGps_returnCaisAlboiAddress() throws ParseException, IOException, URISyntaxException {

        //todo
        String res = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"\\u00A9 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\\u00A9 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.6318,\"lng\":-8.658}},\"locations\":[{\"street\":\"Parque Estacionamento da Reitoria - Univerisdade de Aveiro\",\"adminArea6\":\"\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Gl\u00F3ria e Vera Cruz\",\"adminArea5Type\":\"City\",\"adminArea4\":\"\",\"adminArea4Type\":\"County\",\"adminArea3\":\"Centro\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-193\",\"geocodeQualityCode\":\"P1AAA\",\"geocodeQuality\":\"POINT\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.631803,\"lng\":-8.657881},\"displayLatLng\":{\"lat\":40.631803,\"lng\":-8.657881},\"mapUrl\":\"http://open.mapquestapi.com/staticmap/v5/map?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&type=map&size=225,160&locations=40.6318025,-8.657881|marker-sm-50318A-1&scalebar=true&zoom=15&rand=981709776\",\"roadMetadata\":null}]}]}";
        when(httpClient.doHttpGet(any(String.class))).thenReturn(res);

        //e.g.
        Optional<Address> result = resolver.findAddressForLocation(40.631803, -8.657881);

        assertEquals(result.get(), new Address("Parque Estacionamento da Reitoria - Univerisdade de Aveiro", "Glória e Vera Cruz", "Centro", "3810-193", null));
        assertTrue(result.isPresent());
        assertEquals(result.get().getCirty(), "Glória e Vera Cruz");
        assertEquals(result.get().getHouseNumber(), null);
        assertEquals(result.get().getRoad(), "Parque Estacionamento da Reitoria - Univerisdade de Aveiro");
        assertEquals(result.get().getState(), "Centro");
        assertEquals(result.get().getZio(), "3810-193");
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        assertThrows( IllegalArgumentException.class, () -> { resolver.findAddressForLocation(-300, -810); } );
    }
}