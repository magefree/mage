package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RealityHeist extends CardImpl {

    public RealityHeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}{U}");

        // This spell costs {1} less to cast for each artifact you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionForEachSourceEffect(1, ArtifactYouControlCount.instance)
        ).addHint(ArtifactYouControlHint.instance));

        // Look at the top seven cards of your library. You may reveal up to two artifact cards from among them and put them into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(
                new LookLibraryAndPickControllerEffect(
                        StaticValue.get(5), false, StaticValue.get(1),
                        StaticFilters.FILTER_CARD_ARTIFACT, Zone.LIBRARY, false, true,
                        false, Zone.HAND, true, false, false
                ).setBackInRandomOrder(true)
                        .setText("look at the top seven cards of your library. You may reveal up to " +
                                "two artifact cards from among them and put them into your hand. " +
                                "Put the rest on the bottom of your library in a random order")
        );
    }

    private RealityHeist(final RealityHeist card) {
        super(card);
    }

    @Override
    public RealityHeist copy() {
        return new RealityHeist(this);
    }
}
