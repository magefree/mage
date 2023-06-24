package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FiligreeVector extends CardImpl {

    public FiligreeVector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Filigree Vector enters the battlefield, put a +1/+1 counter on each of any number of target creatures and a charge counter on each of any number of target artifacts.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on each of any number of target creatures")
        );
        ability.addEffect(new AddCountersTargetEffect(CounterType.CHARGE.createInstance())
                .setText("and a charge counter on each of any number of target artifacts")
                .setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        ability.addTarget(new TargetArtifactPermanent(0, Integer.MAX_VALUE));
        this.addAbility(ability);

        // {1}, {T}, Sacrifice another artifact: Proliferate.
        ability = new SimpleActivatedAbility(new ProliferateEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_SHORT_TEXT));
        this.addAbility(ability);
    }

    private FiligreeVector(final FiligreeVector card) {
        super(card);
    }

    @Override
    public FiligreeVector copy() {
        return new FiligreeVector(this);
    }
}
