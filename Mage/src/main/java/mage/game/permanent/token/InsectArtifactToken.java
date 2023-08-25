package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class InsectArtifactToken extends TokenImpl {

    public InsectArtifactToken() {
        super("Insect Token", "1/1 colorless Insect artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    protected InsectArtifactToken(final InsectArtifactToken token) {
        super(token);
    }

    public InsectArtifactToken copy() {
        return new InsectArtifactToken(this);
    }
}