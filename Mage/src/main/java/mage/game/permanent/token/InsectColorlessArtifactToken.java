package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class InsectColorlessArtifactToken extends TokenImpl {

    public InsectColorlessArtifactToken() {
        super("Insect Token", "1/1 colorless Insect artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    protected InsectColorlessArtifactToken(final InsectColorlessArtifactToken token) {
        super(token);
    }

    public InsectColorlessArtifactToken copy() {
        return new InsectColorlessArtifactToken(this);
    }
}