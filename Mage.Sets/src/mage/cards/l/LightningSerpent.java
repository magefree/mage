package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class LightningSerpent extends CardImpl {

    public LightningSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Lightning Serpent enters the battlefield with X +1/+0 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P0.createInstance())));

        // At the beginning of the end step, sacrifice Lightning Serpent.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new SacrificeSourceEffect(), false
        ));
    }

    private LightningSerpent(final LightningSerpent card) {
        super(card);
    }

    @Override
    public LightningSerpent copy() {
        return new LightningSerpent(this);
    }
}
