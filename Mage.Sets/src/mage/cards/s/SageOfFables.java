package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SageOfFables extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.WIZARD, "Wizard creature");

    public SageOfFables(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each other Wizard creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new EntersWithCountersControlledEffect(
                filter, CounterType.P1P1.createInstance(), true
        )));

        // {2}, Remove a +1/+1 counter from a creature you control: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(), CounterType.P1P1));
        this.addAbility(ability);
    }

    private SageOfFables(final SageOfFables card) {
        super(card);
    }

    @Override
    public SageOfFables copy() {
        return new SageOfFables(this);
    }
}
