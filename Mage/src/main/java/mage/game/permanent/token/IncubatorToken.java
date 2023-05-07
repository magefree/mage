package mage.game.permanent.token;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.constants.CardType;
import mage.constants.SubType;

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
    }

    public IncubatorToken(final IncubatorToken token) {
        super(token);
    }

    public IncubatorToken copy() {
        return new IncubatorToken(this);
    }
}
