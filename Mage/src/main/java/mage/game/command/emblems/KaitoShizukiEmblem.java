package mage.game.command.emblems;

import mage.ObjectColor;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.command.Emblem;
import mage.target.common.TargetCardInLibrary;

/**
 * @author TheElk801
 */
public final class KaitoShizukiEmblem extends Emblem {

    private static final FilterCard filter = new FilterCreatureCard("a blue or black creature card");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.BLACK)
        ));
    }

    // −7: You get an emblem with "Whenever a creature you control deals combat damage to a player, search your library for a blue or black creature card, put it onto the battlefield, then shuffle."
    public KaitoShizukiEmblem() {
        super("Emblem Kaito");
        this.getAbilities().add(new DealsDamageToAPlayerAllTriggeredAbility(
                Zone.COMMAND, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, false,
                SetTargetPointer.NONE, true, false
        ));
    }

    private KaitoShizukiEmblem(final KaitoShizukiEmblem card) {
        super(card);
    }

    @Override
    public KaitoShizukiEmblem copy() {
        return new KaitoShizukiEmblem(this);
    }
}
