package mage.cards.w;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class WarRoomStarWars extends CardImpl {
    public WarRoomStarWars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        //At the beginning of each combat on your turn, target creature can't block this turn.
        BeginningOfCombatTriggeredAbility beginningOfCombatTriggeredAbility = new BeginningOfCombatTriggeredAbility(
                new GainAbilityTargetEffect(new CantBlockAbility(), Duration.EndOfTurn)
                        .setText("target creature can't block this turn"), TargetController.YOU, false);
        beginningOfCombatTriggeredAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(beginningOfCombatTriggeredAbility);

        //{1}: War Room deals 1 damage to target player. Activate this ability only if a creature you control attacked
        //this turn and only once per turn.
        LimitedTimesPerTurnActivatedAbility limitedTimesPerTurnActivatedAbility = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(1),
                new ManaCostsImpl<>("{1}"),
                1,
                new WarRoomStarWarsCondition());
        limitedTimesPerTurnActivatedAbility.addTarget(new TargetPlayer());
        this.addAbility(limitedTimesPerTurnActivatedAbility, new AttackedOrBlockedThisCombatWatcher());
    }

    public WarRoomStarWars(final WarRoomStarWars card) {
        super(card);
    }

    @Override
    public WarRoomStarWars copy() {
        return new WarRoomStarWars(this);
    }
}

class WarRoomStarWarsCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedOrBlockedThisCombatWatcher watcher = game.getState().getWatcher(AttackedOrBlockedThisCombatWatcher.class);
        if (game.getActivePlayerId() == source.getControllerId()) {
            if (watcher != null) {
                for (MageObjectReference mor : watcher.getAttackedThisTurnCreatures()) {
                    if (mor.getPermanent(game).getControllerId() == source.getControllerId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if a creature you control attacked this turn";
    }
}
