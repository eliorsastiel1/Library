package elior.library;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class UnitTestSample {
    @Mock User userMock;

    @Test
    public void addition_isCorrect() throws Exception {
        Mockito.when(this.userMock.getEmailAddress()).thenReturn("blabla@gmail.com");
        assertEquals(false,User.isValidEmail(this.userMock));
    }

}