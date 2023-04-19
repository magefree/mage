package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
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
        this.backFace = new Phyrexian00Token();

        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(
                new TransformSourceEffect().setText("transform this artifact"), new GenericManaCost(2)
        ));
        availableImageSetCodes = Arrays.asList("MOM");
    }

    public IncubatorToken(final IncubatorToken token) {
        super(token);
    }

    public IncubatorToken copy() {
        return new IncubatorToken(this);
    }
}

class Phyrexian00Token extends TokenImpl {

    Phyrexian00Token() {
        super("Phyrexian Token", "0/0 Phyrexian artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        power = new MageInt(0);
        toughness = new MageInt(0);

        availableImageSetCodes = Arrays.asList("MOM");
    }

    public Phyrexian00Token(final Phyrexian00Token token) {
        super(token);
    }

    public Phyrexian00Token copy() {
        return new Phyrexian00Token(this);
    }
}
