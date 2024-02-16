package mage.filter.predicate.permanent;

import mage.constants.TurnPhase;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * DefendingPlayerControlsSourceAttackingPredicate is maybe what you are looking for,
 * for modern usage of "defending player", inside an attack trigger, where the 'defending
 * player' makes sense, it is the player attacked by the source of the ability.
 * <p>
 * If not, let's go for a ride on rule interpretation of very old cards hiding in
 * a corner of a gray area of the rules.
 * <p>
 * 506.2. During the combat phase, the active player is the attacking player; creatures that player controls
 * may attack. During the combat phase of a two-player game, the nonactive player is the defending
 * player; that player, planeswalkers they control, and battles they protect may be attacked.
 * 506.2a During the combat phase of a multiplayer game, there may be one or more defending
 * players, depending on the variant being played and the options chosen for it. Unless all the
 * attacking player’s opponents automatically become defending players during the combat phase,
 * the attacking player chooses one of their opponents as a turn-based action during the beginning
 * of combat step. (Note that the choice may be dictated by the variant being played or the options
 * chosen for it.) That player becomes the defending player. See rule 802, “Attack Multiple Players
 * Option,” rule 803, “Attack Left and Attack Right Options,” and rule 809, “Emperor Varian
 * <p>
 * 802. Attack Multiple Players Option
 * 802.1. Some multiplayer games allow the active player to attack multiple other players. If this option is
 * used, a player can also choose to attack only one player during a particular combat.
 * 802.2. As the combat phase starts, the attacking player doesn’t choose an opponent to become the
 * defending player. Instead, all the attacking player’s opponents are defending players during the
 * combat phase.
 * 802.2a Any rule, object, or effect that refers to a “defending player” refers to one specific defending
 * player, not to all of the defending players. If an ability of an attacking creature refers to a
 * defending player, or a spell or ability refers to both an attacking creature and a defending player,
 * then unless otherwise specified, the defending player it’s referring to is the player that creature
 * is attacking, the controller of the planeswalker that creature is attacking, or the protector of the
 * battle that player is attacking. If that creature is no longer attacking, the defending player it’s
 * referring to is the player that creature was attacking before it was removed from combat, the
 * controller of the planeswalker that creature was attacking before it was removed from combat,
 * or the protector of the battle that player was attacking before it was removed from combat. If a
 * spell or ability could apply to multiple attacking creatures, the appropriate defending player is
 * individually determined for each of those attacking creatures. If there are multiple defending
 * players that could be chosen, the controller of the spell or ability chooses one.
 * <p>
 * So after those walls of text, let's go back to the weird case of effects mentioning
 * 'defending player', when the source is not attacking.
 * <p>
 * For instance with: Blaze of Glory
 * Instant {W}
 * Cast this spell only during combat before blockers are declared.
 * Target creature defending player controls can block any number of creatures this turn. It blocks each attacking creature this turn if able.
 * <p>
 * <p>
 * For that card, it does not matter (at least I'm interpreting it that way) that an opponent of the active player
 * be attacked for their creatures to be targettable by Blaze of Glory, as long as it is during the combat step.
 *
 * @author Susucr
 */
public enum DefendingPlayerControlsNoSourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return game.getState().getTurnPhaseType() == TurnPhase.COMBAT &&
                game.getOpponents(game.getActivePlayerId())
                        .contains(input.getObject().getControllerId());
    }

    @Override
    public String toString() {
        return "";
    }
}
