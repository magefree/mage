
package mage.game;

import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.CommanderReplacementEffect;
import mage.abilities.effects.common.cost.CommanderCostModification;
import mage.cards.Card;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.watchers.common.CommanderInfoWatcher;

public abstract class GameCommanderImpl extends GameImpl {

    // private final Map<UUID, Cards> mulliganedCards = new HashMap<>();
    protected boolean checkCommanderDamage = true;
    protected boolean alsoHand;    // replace commander going to hand
    protected boolean alsoLibrary; // replace commander going to library
    protected boolean startingPlayerSkipsDraw = true;

    public GameCommanderImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
    }

    public GameCommanderImpl(final GameCommanderImpl game) {
        super(game);
        this.alsoHand = game.alsoHand;
        this.alsoLibrary = game.alsoLibrary;
        this.startingPlayerSkipsDraw = game.startingPlayerSkipsDraw;
        this.checkCommanderDamage = game.checkCommanderDamage;
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new InfoEffect("Commander effects"));
        //Move commander to command zone
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            if (player != null) {
                while (!player.getSideboard().isEmpty()) {
                    Card commander = this.getCard(player.getSideboard().iterator().next());
                    if (commander != null) {
                        player.addCommanderId(commander.getId());
                        commander.moveToZone(Zone.COMMAND, null, this, true);
                        commander.getAbilities().setControllerId(player.getId());
                        ability.addEffect(new CommanderReplacementEffect(commander.getId(), alsoHand, alsoLibrary));
                        ability.addEffect(new CommanderCostModification(commander.getId()));
                        getState().setValue(commander.getId() + "_castCount", 0);
                        CommanderInfoWatcher watcher = new CommanderInfoWatcher(commander.getId(), checkCommanderDamage);
                        getState().getWatchers().add(watcher);
                        watcher.addCardInfoToCommander(this);
                    }
                }
            }
        }
        this.getState().addAbility(ability, null);
        super.init(choosingPlayerId);
        if (startingPlayerSkipsDraw) {
            state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
        }
    }

    //20130711
    /*903.8. The Commander variant uses an alternate mulligan rule.
     * Each time a player takes a mulligan, rather than shuffling their entire hand of cards into their library, that player exiles any number of cards from their hand face down.
     * Then the player draws a number of cards equal to one less than the number of cards he or she exiled this way.
     * That player may look at all cards exiled this way while taking mulligans.
     * Once a player keeps an opening hand, that player shuffles all cards he or she exiled this way into their library.
     * */
    //TODO implement may look at exile cards
    @Override
    public void mulligan(UUID playerId) {
        super.mulligan(playerId);
        // Paris mulligan - no longer used by default for commander
//        Player player = getPlayer(playerId);
//        TargetCardInHand target = new TargetCardInHand(1, player.getHand().size(), new FilterCard("card to mulligan"));
//        target.setNotTarget(true);
//        target.setRequired(false);
//        if (player.choose(Outcome.Exile, player.getHand(), target, this)) {
//            int numCards = target.getTargets().size();
//            for (UUID uuid : target.getTargets()) {
//                Card card = player.getHand().get(uuid, this);
//                if (card != null) {
//                    if (!mulliganedCards.containsKey(playerId)) {
//                        mulliganedCards.put(playerId, new CardsImpl());
//                    }
//                    player.getHand().remove(card);
//                    getExile().add(card);
//                    getState().setZone(card.getId(), Zone.EXILED);
//                    card.setFaceDown(true, this);
//                    mulliganedCards.get(playerId).add(card);
//                }
//            }
//            int deduction = 1;
//            if (freeMulligans > 0) {
//                if (usedFreeMulligans != null && usedFreeMulligans.containsKey(player.getId())) {
//                    int used = usedFreeMulligans.get(player.getId());
//                    if (used < freeMulligans) {
//                        deduction = 0;
//                        usedFreeMulligans.put(player.getId(), used + 1);
//                    }
//                } else {
//                    deduction = 0;
//                    {
//
//                    }
//                    usedFreeMulligans.put(player.getId(), 1);
//                }
//            }
//            player.drawCards(numCards - deduction, this);
//            fireInformEvent(new StringBuilder(player.getLogName())
//                    .append(" mulligans ")
//                    .append(numCards)
//                    .append(numCards == 1 ? " card" : " cards")
//                    .append(deduction == 0 ? " for free and draws " : " down to ")
//                    .append(Integer.toString(player.getHand().size()))
//                    .append(player.getHand().size() <= 1 ? " card" : " cards").toString());
//        }
    }

    @Override
    public void endMulligan(UUID playerId) {
        super.endMulligan(playerId);
        // Paris mulligan - no longer used by default for commander
//        //return cards to
//        Player player = getPlayer(playerId);
//        if (player != null && mulliganedCards.containsKey(playerId)) {
//            for (Card card : mulliganedCards.get(playerId).getCards(this)) {
//                if (card != null) {
//                    getExile().removeCard(card, this);
//                    player.getLibrary().putOnTop(card, this);
//                    getState().setZone(card.getId(), Zone.LIBRARY);
//                    card.setFaceDown(false, this);
//                }
//            }
//            if (mulliganedCards.get(playerId).size() > 0) {
//                player.shuffleLibrary(null, this);
//            }
//        }
    }

    /* 20130711
     *903.14a A player that's been dealt 21 or more combat damage by the same commander
     * over the course of the game loses the game. (This is a state-based action. See rule 704.)
     *
     */
    @Override
    protected boolean checkStateBasedActions() {
        for (Player player : getPlayers().values()) {
            for (UUID commanderId : player.getCommandersIds()) {
                CommanderInfoWatcher damageWatcher = (CommanderInfoWatcher) getState().getWatchers().get(CommanderInfoWatcher.class.getSimpleName(), commanderId);
                if (damageWatcher == null) {
                    continue;
                }
                for (Map.Entry<UUID, Integer> entrySet : damageWatcher.getDamageToPlayer().entrySet()) {
                    if (entrySet.getValue() > 20) {
                        Player opponent = getPlayer(entrySet.getKey());
                        if (opponent != null && !opponent.hasLost() && player.isInGame()) {
                            opponent.lost(this);
                        }
                    }
                }
            }
        }
        return super.checkStateBasedActions();
    }

    public void setAlsoHand(boolean alsoHand) {
        this.alsoHand = alsoHand;
    }

    public void setAlsoLibrary(boolean alsoLibrary) {
        this.alsoLibrary = alsoLibrary;
    }

    public boolean isCheckCommanderDamage() {
        return checkCommanderDamage;
    }

    public void setCheckCommanderDamage(boolean checkCommanderDamage) {
        this.checkCommanderDamage = checkCommanderDamage;
    }

}
