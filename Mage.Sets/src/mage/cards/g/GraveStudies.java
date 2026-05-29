package mage.cards.g;

import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class GraveStudies extends CardImpl {

    public GraveStudies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");

        // Conjure a card named Teacher's Pest onto the battlefield, then each player sacrifices a creature.
        this.getSpellAbility().addEffect(new ConjureCardEffect("Teacher's Pest", Zone.BATTLEFIELD, 1));
        this.getSpellAbility().addEffect(new SacrificeAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE).concatBy(", then"));
    }

    private GraveStudies(final GraveStudies card) {
        super(card);
    }

    @Override
    public GraveStudies copy() {
        return new GraveStudies(this);
    }
}
