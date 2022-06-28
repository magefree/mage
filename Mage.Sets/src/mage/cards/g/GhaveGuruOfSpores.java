
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public final class GhaveGuruOfSpores extends CardImpl {

    public GhaveGuruOfSpores(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ghave, Guru of Spores enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)), "with five +1/+1 counters on it"));

        // {1}, Remove a +1/+1 counter from a creature you control: Create a 1/1 green Saproling creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new GenericManaCost(1));
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent(), true), CounterType.P1P1));
        this.addAbility(ability);

        // {1}, Sacrifice a creature: Put a +1/+1 counter on target creature.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1));
        ability2.addCost(new SacrificeTargetCost(new TargetControlledPermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
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
