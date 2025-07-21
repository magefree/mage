package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class TheAbyss extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonartifact creature that player controls of their choice");

    public TheAbyss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.supertype.add(SuperType.WORLD);

        // At the beginning of each player's upkeep, destroy target nonartifact creature that player controls of their choice. It can't be regenerated.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(TargetController.EACH_PLAYER,
                new DestroyTargetEffect(true), false).withTargetPointerSet(true);
        ability.addTarget(new TargetPermanent(filter)); // Only used for text generation
        ability.setTargetAdjuster(TheAbyssTargetAdjuster.instance);
        this.addAbility(ability);
    }

    private TheAbyss(final TheAbyss card) {
        super(card);
    }

    @Override
    public TheAbyss copy() {
        return new TheAbyss(this);
    }
}

enum TheAbyssTargetAdjuster implements TargetAdjuster {
    instance;

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("nonartifact creature that player controls of their choice");
    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID playerId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        ability.getTargets().clear();
        ability.getAllEffects().setTargetPointer(new FirstTargetPointer());

        FilterPermanent adjustedFilter = filter.copy();
        adjustedFilter.add(new ControllerIdPredicate(playerId));
        Target newTarget = new TargetPermanent(adjustedFilter);
        newTarget.setTargetController(playerId);
        ability.addTarget(newTarget);
    }
}
