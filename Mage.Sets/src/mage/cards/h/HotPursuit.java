package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.SuspectTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainControlAllUntapGainHasteEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.GoadedPredicate;
import mage.filter.predicate.permanent.SuspectedPredicate;
import mage.game.Game;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.watchers.common.PlayerLostGameWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HotPursuit extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("goaded and/or suspected creatures");

    static {
        filter.add(Predicates.or(
                GoadedPredicate.instance,
                SuspectedPredicate.instance
        ));
    }

    public HotPursuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // When Hot Pursuit enters the battlefield, suspect target creature an opponent controls. As long as Hot Pursuit remains on the battlefield, that creature is also goaded.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SuspectTargetEffect());
        ability.addEffect(new GoadTargetEffect(Duration.UntilSourceLeavesBattlefield)
                .setText("as long as {this} remains on the battlefield, that creature is also goaded"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // At the beginning of combat on your turn, if two or more players have lost the game, gain control of all goaded and/or suspected creatures until end of turn. Untap them. They gain haste until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new GainControlAllUntapGainHasteEffect(filter))
                .withInterveningIf(HotPursuitCondition.instance), new PlayerLostGameWatcher());
    }

    private HotPursuit(final HotPursuit card) {
        super(card);
    }

    @Override
    public HotPursuit copy() {
        return new HotPursuit(this);
    }
}

enum HotPursuitCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return PlayerLostGameWatcher.getCount(game) >= 2;
    }

    @Override
    public String toString() {
        return "two or more players have lost the game";
    }
}
