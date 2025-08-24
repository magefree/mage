package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class MagusOfTheAbyss extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonartifact creature that player controls of their choice");

    public MagusOfTheAbyss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // At the beginning of each player's upkeep, destroy target nonartifact creature that player controls of their choice. It can't be regenerated.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(TargetController.EACH_PLAYER,
                new DestroyTargetEffect(true), false).withTargetPointerSet(true);
        ability.addTarget(new TargetPermanent(filter)); // Only used for text generation
        ability.setTargetAdjuster(MagusOfTheAbyssTargetAdjuster.instance);
        this.addAbility(ability);
    }

    private MagusOfTheAbyss(final MagusOfTheAbyss card) {
        super(card);
    }

    @Override
    public MagusOfTheAbyss copy() {
        return new MagusOfTheAbyss(this);
    }
}

enum MagusOfTheAbyssTargetAdjuster implements TargetAdjuster {
    instance;

    private static final FilterPermanent filter
            = new FilterPermanent("nonartifact creature that player controls of their choice");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(CardType.CREATURE.getPredicate());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID opponentId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        ability.getTargets().clear();
        ability.getAllEffects().setTargetPointer(new FirstTargetPointer());

        FilterPermanent adjustedFilter = filter.copy();
        adjustedFilter.add(new ControllerIdPredicate(opponentId));
        Target newTarget = new TargetPermanent(adjustedFilter);
        newTarget.setTargetController(opponentId);
        ability.addTarget(newTarget);
    }
}
