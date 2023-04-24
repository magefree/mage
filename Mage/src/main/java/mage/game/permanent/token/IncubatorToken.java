package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class IncubatorToken extends TokenImpl {

    public IncubatorToken() {
        super("Incubator Token", "Incubator artifact token with \"{2}: Transform this artifact.\"");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.INCUBATOR);

        // TODO: Implement this correctly

        availableImageSetCodes = Arrays.asList("MOM");
    }

    public IncubatorToken(final IncubatorToken token) {
        super(token);
    }

    public IncubatorToken copy() {
        return new IncubatorToken(this);
    }
}
