package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.OpponentsPoisonCountersCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.PhyrexianMiteToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class VishgrazTheDoomhive extends CardImpl {

    public VishgrazTheDoomhive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN, SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // When Vishgraz, the Doomhive enters the battlefield, create three 1/1 colorless Phyrexian
        // Mite artifact creature tokens with toxic 1 and "This creature can't block."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PhyrexianMiteToken(), 3), false));

        // Vishgraz gets +1/+1 for each poison counter your opponents have.
        DynamicValue value = OpponentsPoisonCountersCount.instance;
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(value, value, Duration.WhileOnBattlefield)));
    }

    private VishgrazTheDoomhive(final VishgrazTheDoomhive card) {
        super(card);
    }

    @Override
    public VishgrazTheDoomhive copy() {
        return new VishgrazTheDoomhive(this);
    }
}
