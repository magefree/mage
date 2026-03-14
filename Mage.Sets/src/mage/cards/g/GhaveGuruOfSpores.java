
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public final class GhaveGuruOfSpores extends CardImpl {

    public GhaveGuruOfSpores(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ghave, Guru of Spores enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance(5)));

        // {1}, Remove a +1/+1 counter from a creature you control: Create a 1/1 green Saproling creature token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new SaprolingToken()), new GenericManaCost(1));
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent().withNotTarget(true), CounterType.P1P1));
        this.addAbility(ability);

        // {1}, Sacrifice a creature: Put a +1/+1 counter on target creature.
        Ability ability2 = new SimpleActivatedAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1));
        ability2.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    private GhaveGuruOfSpores(final GhaveGuruOfSpores card) {
        super(card);
    }

    @Override
    public GhaveGuruOfSpores copy() {
        return new GhaveGuruOfSpores(this);
    }

}
