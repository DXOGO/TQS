package integration;

import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolver;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressResolverIT {

    private AddressResolver resolver;

    @BeforeEach
    public void init(){ this.resolver = new AddressResolver(new TqsBasicHttpClient()); }

    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        //todo

        // repeat the same tests conditions from AddressResolverTest, without mocks

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
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        //todo
        // repeat the same tests conditions from AddressResolverTest, without mocks

        assertThrows( IllegalArgumentException.class, () -> { resolver.findAddressForLocation(-300, -810); } );
        
    }

}
