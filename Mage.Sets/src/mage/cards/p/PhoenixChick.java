package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class PhoenixChick extends CardImpl {

    public PhoenixChick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Phoenix Chick can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever you attack with three or more creatures, you may pay {R}{R}. If you do, return Phoenix Chick from your graveyard to the battlefield tapped and attacking with a +1/+1 counter on it.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(
                        new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(CounterType.P1P1.createInstance(), true, false, false, true),
                        new ManaCostsImpl<>("{R}{R}")
                ),
                3,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
    }

    private PhoenixChick(final PhoenixChick card) {
        super(card);
    }

    @Override
    public PhoenixChick copy() {
        return new PhoenixChick(this);
    }
}
