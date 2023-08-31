package mage.cards.m;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetDefender;

/**
 *
 * @author Xanderhall
 */
public final class MisleadingSignpost extends CardImpl {

    public MisleadingSignpost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");
        

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Misleading Signpost enters the battlefield during the declare attackers step, you may reselect which player or permanent target attacking creature is attacking.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new MisleadingSignpostEffect(), true),
                new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false),
                "When {this} enters the battlefield during the declare attackers step, you may reselect which player or planeswalker target attacking creature is attacking. "
                + "<i>(It can't attack its controller or their permanents)</i>");
        ability.addTarget(new TargetCreaturePermanent(new FilterAttackingCreature()));
        this.addAbility(ability);

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private MisleadingSignpost(final MisleadingSignpost card) {
        super(card);
    }

    @Override
    public MisleadingSignpost copy() {
        return new MisleadingSignpost(this);
    }
}

class MisleadingSignpostEffect extends OneShotEffect {

    public MisleadingSignpostEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may reselect which player or permanent target attacking creature is attacking";
    }

    public MisleadingSignpostEffect(final MisleadingSignpostEffect effect) {
        super(effect);
    }

    @Override
    public MisleadingSignpostEffect copy() {
        return new MisleadingSignpostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent attackingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || attackingCreature == null) {
            return false;
        }
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

        return false;
    }
}