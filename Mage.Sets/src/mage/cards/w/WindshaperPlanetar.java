package mage.cards.w;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

// Abilities
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;

// Cards
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

// Constants
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubType;

// Filter
import mage.filter.common.FilterAttackingCreature;
import mage.filter.StaticFilters;

// Game
import mage.game.combat.CombatGroup;
import mage.game.Game;
import mage.game.permanent.Permanent;

// Base
import mage.MageInt;

// Players
import mage.players.Player;
import mage.players.PlayerList;

// Target
import mage.target.common.TargetDefender;

/**
 *
 * @author LevelX2
 */
public final class WindshaperPlanetar extends CardImpl {

  public WindshaperPlanetar(UUID ownerId, CardSetInfo setInfo) {
    super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{4}{W}");

    this.subtype.add(SubType.ANGEL);

    this.power = new MageInt(4);
    this.toughness = new MageInt(4);

    // Flash
    this.addAbility(FlashAbility.getInstance());
    // Flying
    this.addAbility(FlyingAbility.getInstance());

    // When Windshaper Planetar enters the battlefield during the declare attackers
    // step, for each attacking creature, you may reselect which player or
    // planeswalker that creature is attacking. (It can't attack its controller
    // or its controller's planeswalkers.)
    Ability ability = new ConditionalTriggeredAbility(
        new EntersBattlefieldTriggeredAbility(new WindshaperPlanetarEffect(), true),
        new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false),
        "When {this} enters the battlefield during the declare attackers step, for each attacking creature, you may reselect which player or planeswalker that creature is attacking. "
            + "<i>(It can't attack its controller or its controller's planeswalkers.)</i>");

    this.addAbility(ability);
  }

  private WindshaperPlanetar(final WindshaperPlanetar card) {
    super(card);
  }

  @Override
  public WindshaperPlanetar copy() {
    return new WindshaperPlanetar(this);
  }
}

class WindshaperPlanetarEffect extends OneShotEffect {

  public WindshaperPlanetarEffect() {
    super(Outcome.Benefit);
    this.staticText = "you may reselect which player or planeswalker target attacking creature is attacking";
  }

  public WindshaperPlanetarEffect(final WindshaperPlanetarEffect effect) {
    super(effect);
  }

  @Override
  public WindshaperPlanetarEffect copy() {
    return new WindshaperPlanetarEffect(this);
  }

  @Override
  public boolean apply(Game game, Ability source) {
    game.getPlayerList();
    Player controller = game.getPlayer(source.getControllerId());

    if (controller == null) {
      return false;
    }

    PlayerList playerList = game.getState().getPlayersInRange(controller.getId(), game);
    Player activePlayer = game.getPlayer(game.getActivePlayerId());

    Player currentPlayer = activePlayer;

    do {
      List<Permanent> currentPlayerAttackingCreatures = game.getState().getBattlefield()
          .getActivePermanents(new FilterAttackingCreature(), currentPlayer.getId(), game);

      for (Permanent attackingCreature : currentPlayerAttackingCreatures) {
        if (attackingCreature != null) {
          CombatGroup combatGroupTarget = null;

          for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().contains(attackingCreature.getId())) {
              combatGroupTarget = combatGroup;
              break;
            }
          }

          if (combatGroupTarget == null) {
            continue;
          }

          Set<UUID> possibleDefenders = new LinkedHashSet<>();

          for (UUID playerId : game.getCombat().getAttackablePlayers(game)) {
            if (playerId != attackingCreature.getControllerId()) {
              possibleDefenders.add(playerId);

              List<Permanent> playersPlaneswalkers = game.getBattlefield()
                  .getAllActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, playerId, game);

              for (Permanent permanent : playersPlaneswalkers) {
                possibleDefenders.add(permanent.getId());
              }
            }
          }

          TargetDefender target = new TargetDefender(possibleDefenders, null);

          if (controller.chooseTarget(Outcome.Damage, target, source, game)) {
            UUID targetDefender = target.getFirstTarget();

            if (!combatGroupTarget.getDefenderId().equals(targetDefender)) { // Target has changed
              if (combatGroupTarget.changeDefenderPostDeclaration(targetDefender, game)) {
                String attacked = "";
                Player player = game.getPlayer(targetDefender);

                if (player != null) {
                  attacked = player.getLogName();
                } else {
                  Permanent planeswalker = game.getPermanent(targetDefender);

                  if (planeswalker != null) {
                    attacked = planeswalker.getLogName();
                  }
                }

                game.informPlayers(attackingCreature.getLogName() + " now attacks " + attacked);
              }
            }
          }
        }
      }

      currentPlayer = playerList.getNext(game, false);
    } while (currentPlayer != null
        && !currentPlayer.getId().equals(game.getActivePlayerId()) // Back to the beginning
        && activePlayer.canRespond());

    return true;
  }
}
