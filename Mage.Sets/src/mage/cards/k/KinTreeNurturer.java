package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.EndureSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KinTreeNurturer extends CardImpl {

    public KinTreeNurturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When this creature enters, it endures 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EndureSourceEffect(1)));
    }

    private KinTreeNurturer(final KinTreeNurturer card) {
        super(card);
    }

    @Override
    public KinTreeNurturer copy() {
        return new KinTreeNurturer(this);
    }
}
