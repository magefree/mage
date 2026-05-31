package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author ChesseTheWasp
 */
public final class ArtifactShapeshifterToken extends TokenImpl {

    public ArtifactShapeshifterToken() {
        super("Shapeshifter Token", "0/1 colorless Artifact Shapeshifter");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.SHAPESHIFTER);
        this.addAbility(new ChangelingAbility());
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    private ArtifactShapeshifterToken(final ArtifactShapeshifterToken token) {
        super(token);
    }

    public ArtifactShapeshifterToken copy() {
        return new ArtifactShapeshifterToken(this);
    }
}
