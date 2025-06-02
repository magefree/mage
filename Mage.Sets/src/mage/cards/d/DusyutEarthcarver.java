package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.EndureSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DusyutEarthcarver extends CardImpl {

    public DusyutEarthcarver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature enters, it endures 3.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EndureSourceEffect(3)));
    }

    private DusyutEarthcarver(final DusyutEarthcarver card) {
        super(card);
    }

    @Override
    public DusyutEarthcarver copy() {
        return new DusyutEarthcarver(this);
    }
}
