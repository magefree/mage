package mage.game;

import mage.abilities.Ability;
import mage.abilities.common.SignatureSpellCastOnlyWithOathbreakerEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OathbreakerOnBattlefieldCondition;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.Card;
import mage.constants.CommanderCardType;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;
import mage.players.Player;
import mage.watchers.common.CommanderInfoWatcher;

import java.util.*;

/**
 * @author JayDi85
 */
public class OathbreakerFreeForAll extends GameCommanderImpl {

    private int numPlayers;
    private Map<UUID, UUID> playerSignatureSpell = new HashMap<>();
    private Map<UUID, List<UUID>> playerCommanders = new HashMap<>();

    public OathbreakerFreeForAll(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife);
    }

    public OathbreakerFreeForAll(final OathbreakerFreeForAll game) {
        super(game);
        this.numPlayers = game.numPlayers;
        this.playerSignatureSpell.putAll(game.playerSignatureSpell);
        game.playerCommanders.forEach((key, value) -> this.playerCommanders.put(key, new ArrayList<>(value)));
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        /*
        // prepare commanders and signature spells info
        playerSignatureSpell.clear();
        playerCommanders.clear();
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            UUID signatureSpell = null;
            List<UUID> commanders = new ArrayList<>();

            Player player = getPlayer(playerId);
            List<UUID> searchList = new ArrayList<>();
            searchList.addAll(player.getCommandersIds());
            searchList.addAll(new ArrayList<>(player.getSideboard()));
            for (UUID id : searchList) {
                Card commander = this.getCard(id);
                if (commander != null) {
                    if (commander.isInstantOrSorcery()) {
                        signatureSpell = commander.getId();
                    } else if (!commanders.contains(commander.getId())) {
                        commanders.add(commander.getId());
                    }
                }
            }

            playerSignatureSpell.put(playerId, signatureSpell);
            playerCommanders.put(playerId, commanders);
        }
         */

        // init base commander game
        startingPlayerSkipsDraw = false;
        super.init(choosingPlayerId);
    }

    @Override
    public CommanderInfoWatcher initCommanderWatcher(Card commander, boolean checkCommanderDamage) {
        String commanderType;
        if (commander.isInstantOrSorcery()) {
            commanderType = "Signature Spell";
        } else {
            commanderType = "Oathbreaker";
        }
        return new CommanderInfoWatcher(commanderType, commander.getId(), checkCommanderDamage);
    }

    @Override
    public void initCommanderEffects(Card commander, Player player, Ability commanderAbility) {
        // all commander effects must be independent from sourceId or controllerId
        super.initCommanderEffects(commander, player, commanderAbility);

        // signature spell restrict (spell can be casted on player's commander on battlefield)
        if (commander.getId().equals(this.playerSignatureSpell.getOrDefault(player.getId(), null))) {
            Condition condition = new OathbreakerOnBattlefieldCondition(player.getId(), this.playerCommanders.getOrDefault(player.getId(), null));
            commanderAbility.addEffect(new SignatureSpellCastOnlyWithOathbreakerEffect(condition, commander.getId()));

            // hint must be added to card, not global ability
            Ability ability = new SimpleStaticAbility(new InfoEffect("Signature spell hint"));
            ability.addHint(new ConditionHint(condition, "Oathbreaker on battlefield"));
            ability.setRuleVisible(false);
            commander.addAbility(ability);
        }
    }

    @Override
    public void addCommander(Card card, Player player) {
        super.addCommander(card, player);

        // prepare signature and commanders info
        if (card.isInstantOrSorcery()) {
            this.playerSignatureSpell.put(player.getId(), card.getId());
        } else {
            List<UUID> list = this.playerCommanders.getOrDefault(player.getId(), null);
            if (list == null) {
                list = new ArrayList<>();
                this.playerCommanders.put(player.getId(), list);
            }
            if (!list.contains(card.getId())) {
                list.add(card.getId());
            }
        }
    }

    @Override
    public MatchType getGameType() {
        return new OathbreakerFreeForAllType();
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public OathbreakerFreeForAll copy() {
        return new OathbreakerFreeForAll(this);
    }

    @Override
    public Set<UUID> getCommandersIds(Player player, CommanderCardType commanderCardType) {
        Set<UUID> res = new HashSet<>();
        if (player != null) {
            List<UUID> commanders = this.playerCommanders.getOrDefault(player.getId(), new ArrayList<>());
            UUID spell = this.playerSignatureSpell.getOrDefault(player.getId(), null);
            for (UUID id : player.getCommandersIds()) {
                switch (commanderCardType) {
                    case ANY:
                        res.add(id);
                        break;
                    case COMMANDER_OR_OATHBREAKER:
                        if (commanders.contains(id)) {
                            res.add(id);
                        }
                        break;
                    case SIGNATURE_SPELL:
                        if (id.equals(spell)) {
                            res.add(id);
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unknown commander type " + commanderCardType);
                }
            }
        }
        return res;
    }
}
