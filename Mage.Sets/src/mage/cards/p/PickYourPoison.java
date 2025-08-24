package mage.cards.p;

import mage.abilities.Mode;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PickYourPoison extends CardImpl {

    public PickYourPoison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Choose one --
        // * Each opponent sacrifices an artifact.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT));

        // * Each opponent sacrifices an enchantment.
        this.getSpellAbility().addMode(new Mode(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_ENCHANTMENT)));

        // * Each opponent sacrifices a creature with flying.
        this.getSpellAbility().addMode(new Mode(new SacrificeOpponentsEffect(StaticFilters.FILTER_CREATURE_FLYING)
                .setText("each opponent sacrifices a creature of their choice with flying")));
    }

    private PickYourPoison(final PickYourPoison card) {
        super(card);
    }

    @Override
    public PickYourPoison copy() {
        return new PickYourPoison(this);
    }
}
