package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author spjspj
 */
public final class KarnConstructToken extends TokenImpl {

    public KarnConstructToken() {
        super("Construct Token", "0/0 colorless Construct artifact creature token with \"This creature gets +1/+1 for each artifact you control.\"");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(0);
        toughness = new MageInt(0);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(ArtifactYouControlCount.instance, ArtifactYouControlCount.instance, Duration.WhileOnBattlefield)
                        .setText("This creature gets +1/+1 for each artifact you control")
        ));
    }

    public KarnConstructToken(final KarnConstructToken token) {
        super(token);
    }

    public KarnConstructToken copy() {
        return new KarnConstructToken(this);
    }
}
