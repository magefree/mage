
package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetDefender;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PortalMage extends CardImpl {

    public PortalMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Portal Mage enters the battlefield during the declare attackers step, you may reselect which player or planeswalker target attacking creature is attacking.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new PortalMageEffect(), true),
                new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false),
                "When {this} enters the battlefield during the declare attackers step, you may reselect which player or planeswalker target attacking creature is attacking. "
                + "<i>(It can't attack its controller or its controller's planeswalkers.)</i>");
        ability.addTarget(new TargetCreaturePermanent(new FilterAttackingCreature()));
        this.addAbility(ability);
    }

    private PortalMage(final PortalMage card) {
        super(card);
    }

    @Override
    public PortalMage copy() {
        return new PortalMage(this);
    }
}

class PortalMageEffect extends OneShotEffect {

    public PortalMageEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may reselect which player or planeswalker target attacking creature is attacking";
    }

    public PortalMageEffect(final PortalMageEffect effect) {
        super(effect);
    }

    @Override
    public PortalMageEffect copy() {
        return new PortalMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent attackingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (attackingCreature != null) {
                CombatGroup combatGroupTarget = null;
                for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                    if (combatGroup.getAttackers().contains(attackingCreature.getId())) {
                        combatGroupTarget = combatGroup;
                        break;
                    }
                }
                if (combatGroupTarget == null) {
                    return false;
                }
                // Reselecting which player or planeswalker a creature is attacking ignores all requirements,
                // restrictions, and costs associated with attacking.

                // Update possible defender
                Set<UUID> defenders = new LinkedHashSet<>();
                for (UUID playerId : game.getCombat().getAttackablePlayers(game)) {
                    defenders.add(playerId);
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, playerId, game)) {
                        defenders.add(permanent.getId());
                    }
                }
                // Select the new defender
                TargetDefender target = new TargetDefender(defenders);
                if (controller.chooseTarget(Outcome.Damage, target, source, game)) {
                    if (combatGroupTarget.getDefenderId() != null && !combatGroupTarget.getDefenderId().equals(target.getFirstTarget())) {
                        if (combatGroupTarget.changeDefenderPostDeclaration(target.getFirstTarget(), game)) {
                            String attacked = "";
                            Player player = game.getPlayer(target.getFirstTarget());
                            if (player != null) {
                                attacked = player.getLogName();
                            } else {
                                Permanent permanent = game.getPermanent(target.getFirstTarget());
                                if (permanent != null) {
                                    attacked = permanent.getLogName();
                                }
                            }
                            game.informPlayers(attackingCreature.getLogName() + " now attacks " + attacked);
                            return true;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
