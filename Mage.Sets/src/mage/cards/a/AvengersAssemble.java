package mage.cards.a;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

/**
 *
 * @author muz
 */
public final class AvengersAssemble extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.HERO, "Heroes");

    public AvengersAssemble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Heroes you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter)));

        // At the beginning of each end step, if you attacked with a Hero this turn or a Hero entered the battlefield under your control this turn, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.ANY, new DrawCardSourceControllerEffect(1), false, AvengersAssembleCondition.instance
        ), new PermanentsEnteredBattlefieldWatcher());
    }

    private AvengersAssemble(final AvengersAssemble card) {
        super(card);
    }

    @Override
    public AvengersAssemble copy() {
        return new AvengersAssemble(this);
    }
}

enum AvengersAssembleCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return attackedWithHero(game, source) || heroEnteredUnderYourControl(game, source);
    }

    private static boolean attackedWithHero(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        return watcher.getAttackedThisTurnCreaturesPermanentLKI()
            .stream()
            .anyMatch(p -> p != null
                && p.isControlledBy(source.getControllerId())
                && p.hasSubtype(SubType.HERO, game));
    }

    private static boolean heroEnteredUnderYourControl(Game game, Ability source) {
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Permanent> entered = watcher.getThisTurnEnteringPermanents(source.getControllerId());
        return entered != null && entered.stream().anyMatch(p -> p.hasSubtype(SubType.HERO, game));
    }

    @Override
    public String toString() {
        return "you attacked with a Hero this turn or a Hero entered the battlefield under your control this turn";
    }
}
