package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author L_J
 */
public final class RagingRiver extends CardImpl {

    public RagingRiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{R}");

        // Whenever one or more creatures you control attack, each defending player divides all creatures without flying they control into a "left" pile and a "right" pile. Then, for each attacking creature you control, choose "left" or "right." That creature can't be blocked this combat except by creatures with flying and creatures in a pile with the chosen label.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new RagingRiverEffect(), 1
        ).setTriggerPhrase("Whenever one or more creatures you control attack, "));
    }

    private RagingRiver(final RagingRiver card) {
        super(card);
    }

    @Override
    public RagingRiver copy() {
        return new RagingRiver(this);
    }
}

class RagingRiverEffect extends OneShotEffect {

    public RagingRiverEffect() {
        super(Outcome.Detriment);
        staticText = "each defending player divides all creatures without flying they control into a \"left\" pile and a \"right\" pile. Then, for each attacking creature you control, choose \"left\" or \"right.\" That creature can't be blocked this combat except by creatures with flying and creatures in a pile with the chosen label";
    }

    public RagingRiverEffect(final RagingRiverEffect effect) {
        super(effect);
    }

    @Override
    public RagingRiverEffect copy() {
        return new RagingRiverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> left = new ArrayList<>();
            List<Permanent> right = new ArrayList<>();

            for (UUID defenderId : game.getCombat().getPlayerDefenders(game)) {
                Player defender = game.getPlayer(defenderId);
                if (defender != null) {
                    List<Permanent> leftLog = new ArrayList<>();
                    List<Permanent> rightLog = new ArrayList<>();
                    FilterControlledCreaturePermanent filterBlockers = new FilterControlledCreaturePermanent("creatures without flying you control to assign to the \"left\" pile (creatures not chosen will be assigned to the \"right\" pile)");
                    filterBlockers.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
                    Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filterBlockers, true);
                    if (target.canChoose(defenderId, source, game)) {
                        if (defender.chooseTarget(Outcome.Neutral, target, source, game)) {
                            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), defenderId, game)) {
                                if (target.getTargets().contains(permanent.getId())) {
                                    left.add(permanent);
                                    leftLog.add(permanent);
                                } else if (filterBlockers.match(permanent, defenderId, source, game)) {
                                    right.add(permanent);
                                    rightLog.add(permanent);
                                }
                            }
                        }

                        // it could be nice to invoke some graphic indicator of which creature is Left or Right in this spot
                        StringBuilder sb = new StringBuilder("Left pile of ").append(defender.getLogName()).append(": ");
                        sb.append(leftLog.stream().map(MageObject::getLogName).collect(Collectors.joining(", ")));

                        game.informPlayers(sb.toString());

                        sb = new StringBuilder("Right pile of ").append(defender.getLogName()).append(": ");
                        sb.append(rightLog.stream().map(MageObject::getLogName).collect(Collectors.joining(", ")));

                        game.informPlayers(sb.toString());
                    }
                }
            }

            for (UUID attackers : game.getCombat().getAttackers()) {
                Permanent attacker = game.getPermanent(attackers);
                if (attacker != null && Objects.equals(attacker.getControllerId(), controller.getId())) {
                    CombatGroup combatGroup = game.getCombat().findGroup(attacker.getId());
                    if (combatGroup != null) {
                        FilterCreaturePermanent filter = new FilterCreaturePermanent();
                        Player defender = game.getPlayer(combatGroup.getDefendingPlayerId());
                        if (defender != null) {
                            if (left.isEmpty() && right.isEmpty()) {
                                // shortcut in case of no labeled blockers available
                                filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
                            } else {
                                List<Permanent> leftLog = left.stream()
                                        .filter(permanent -> permanent.getControllerId() != null)
                                        .filter(permanent -> permanent.isControlledBy(defender.getId()))
                                        .collect(Collectors.toList());
                                List<Permanent> rightLog = right.stream()
                                        .filter(permanent -> permanent.getControllerId() != null)
                                        .filter(permanent -> permanent.isControlledBy(defender.getId()))
                                        .collect(Collectors.toList());


                                if (controller.choosePile(outcome, attacker.getName() + ": attacking " + defender.getName(), leftLog, rightLog, game)) {
                                    filter.add(Predicates.not(Predicates.or(new AbilityPredicate(FlyingAbility.class), new PermanentReferenceInCollectionPredicate(left, game))));
                                    game.informPlayers(attacker.getLogName() + ": attacks left (" + defender.getLogName() + ")");
                                } else {
                                    filter.add(Predicates.not(Predicates.or(new AbilityPredicate(FlyingAbility.class), new PermanentReferenceInCollectionPredicate(right, game))));
                                    game.informPlayers(attacker.getLogName() + ": attacks right (" + defender.getLogName() + ")");
                                }
                            }
                            RestrictionEffect effect = new CantBeBlockedByAllTargetEffect(filter, Duration.EndOfCombat);
                            effect.setTargetPointer(new FixedTarget(attacker.getId(), game));
                            game.addEffect(effect, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
