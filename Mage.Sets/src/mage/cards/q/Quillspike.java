
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth

 */
public final class Quillspike extends CardImpl {

    public Quillspike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B/G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {BG}, Remove a -1/-1 counter from a creature you control: Quillspike gets +3/+3 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, 3, Duration.EndOfTurn), new ManaCostsImpl<>("{B/G}"));
        TargetPermanent target = new TargetPermanent(1, 1, StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED, true);
        ability.addCost(new RemoveCounterCost(target, CounterType.M1M1));
        this.addAbility(ability);
        
    }

    private Quillspike(final Quillspike card) {
        super(card);
    }

    @Override
    public Quillspike copy() {
        return new Quillspike(this);
    }
}
