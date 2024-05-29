package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainRetraceYourGraveyardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DeeprootHistorian extends CardImpl {

    private static final FilterCard filter = new FilterCard("Merfolk and Druid cards");

    static {
        filter.add(Predicates.or(SubType.MERFOLK.getPredicate(), SubType.DRUID.getPredicate()));
    }

    public DeeprootHistorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Merfolk and Druid cards in your graveyard have retrace.
        this.addAbility(new SimpleStaticAbility(
                new GainRetraceYourGraveyardEffect(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY)
        ));
    }

    private DeeprootHistorian(final DeeprootHistorian card) {
        super(card);
    }

    @Override
    public DeeprootHistorian copy() {
        return new DeeprootHistorian(this);
    }
}