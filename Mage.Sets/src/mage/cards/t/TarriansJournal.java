package mage.cards.t;

import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TarriansJournal extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another artifact or creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public TarriansJournal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.secondSideCardClazz = mage.cards.t.TheTombOfAclazotz.class;

        // {T}, Sacrifice another artifact or creature: Draw a card. Activate only as a sorcery.
        ActivatedAbility ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new TapSourceCost()
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.setTiming(TimingRule.SORCERY);
        this.addAbility(ability);

        // {2}, {T}, Discard your hand: Transform Tarrian's Journal.
        this.addAbility(new TransformAbility());
        ability = new SimpleActivatedAbility(
                new TransformSourceEffect(),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardHandCost());
        this.addAbility(ability);
    }

    private TarriansJournal(final TarriansJournal card) {
        super(card);
    }

    @Override
    public TarriansJournal copy() {
        return new TarriansJournal(this);
    }
}
