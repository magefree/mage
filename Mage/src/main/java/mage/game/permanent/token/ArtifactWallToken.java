package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class ArtifactWallToken extends TokenImpl {

    public ArtifactWallToken() {
        super("Wall Token", "0/4 colorless Wall artifact creature token with defender");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        power = new MageInt(0);
        toughness = new MageInt(4);

        addAbility(DefenderAbility.getInstance());
    }

    private ArtifactWallToken(final ArtifactWallToken token) {
        super(token);
    }

    public ArtifactWallToken copy() {
        return new ArtifactWallToken(this);
    }
}
