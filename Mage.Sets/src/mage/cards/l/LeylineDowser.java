package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeylineDowser extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("an untapped legendary creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public LeylineDowser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {T}: Mill a card. You may put an instant or sorcery card milled this way into your hand.
        Ability ability = new SimpleActivatedAbility(
                new MillThenPutInHandEffect(1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Tap an untapped legendary creature you control: Untap Leyline Dowser.
        this.addAbility(new SimpleActivatedAbility(
                new UntapSourceEffect(), new TapTargetCost(new TargetControlledPermanent(filter))
        ));
    }

    private LeylineDowser(final LeylineDowser card) {
        super(card);
    }

    @Override
    public LeylineDowser copy() {
        return new LeylineDowser(this);
    }
}
