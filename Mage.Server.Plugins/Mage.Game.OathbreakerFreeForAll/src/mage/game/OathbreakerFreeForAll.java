package mage.game;

import mage.abilities.Ability;
import mage.abilities.common.SignatureSpellCastOnlyWithOathbreakerEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OathbreakerOnBattlefieldCondition;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.CommanderReplacementEffect;
import mage.abilities.effects.common.cost.CommanderCostModification;
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
    private Map<UUID, Set<UUID>> playerSignatureSpells = new HashMap<>();
    private Map<UUID, Set<UUID>> playerOathbreakers = new HashMap<>();

    private static final String COMMANDER_NAME_OATHBREAKER = "Oathbreaker";
    private static final String COMMANDER_NAME_SIGNATURE_SPELL = "Signature Spell";

    public OathbreakerFreeForAll(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife);
        this.startingPlayerSkipsDraw = false;
    }

    public OathbreakerFreeForAll(final OathbreakerFreeForAll game) {
        super(game);
        this.numPlayers = game.numPlayers;
        game.playerSignatureSpells.forEach((key, value) -> this.playerSignatureSpells.put(key, new HashSet<>(value)));
        game.playerOathbreakers.forEach((key, value) -> this.playerOathbreakers.put(key, new HashSet<>(value)));
    }

    private String getCommanderTypeName(Card commander) {
        return commander.isInstantOrSorcery() ? COMMANDER_NAME_SIGNATURE_SPELL : COMMANDER_NAME_OATHBREAKER;
    }

    @Override
    public CommanderInfoWatcher initCommanderWatcher(Card commander, boolean checkCommanderDamage) {
        return new CommanderInfoWatcher(getCommanderTypeName(commander), commander.getId(), checkCommanderDamage);
    }

    @Override
    public void initCommanderEffects(Card commander, Player player, Ability commanderAbility) {
        // all commander effects must be independent from sourceId or controllerId (it's limitation of current commander effects)

        boolean isSignatureSpell = this.playerSignatureSpells.getOrDefault(player.getId(), new HashSet<>()).contains(commander.getId());

        // basic commmander restrict (oathbreaker may ask to move, signature force to move)
        commanderAbility.addEffect(new CommanderReplacementEffect(commander.getId(), alsoHand, alsoLibrary, isSignatureSpell, getCommanderTypeName(commander)));
        commanderAbility.addEffect(new CommanderCostModification(commander.getId()));

        // signature spell restrict (spell can be casted on player's commander on battlefield)
        if (isSignatureSpell) {
            OathbreakerOnBattlefieldCondition condition = new OathbreakerOnBattlefieldCondition(this, player.getId(), commander.getId(),
                    this.playerOathbreakers.getOrDefault(player.getId(), new HashSet<>()));
            commanderAbility.addEffect(new SignatureSpellCastOnlyWithOathbreakerEffect(condition, commander.getId()));

            // hint must be added to card, not global ability
            Ability ability = new SimpleStaticAbility(new InfoEffect("Signature spell hint"));
            ability.addHint(new ConditionHint(condition, "Oathbreaker on battlefield (" + condition.getCompatibleNames() + ")"));
            ability.setRuleVisible(false);
            commander.addAbility(ability);
        }
    }

    private void addInnerCommander(Map<UUID, Set<UUID>> destList, UUID playerId, UUID cardId) {
        Set<UUID> list = destList.getOrDefault(playerId, null);
        if (list == null) {
            list = new HashSet<>();
            destList.put(playerId, list);
        }
        list.add(cardId);
    }

    @Override
    public void addCommander(Card card, Player player) {
        super.addCommander(card, player);

        // prepare signature and commanders info
        if (card.isInstantOrSorcery()) {
            addInnerCommander(this.playerSignatureSpells, player.getId(), card.getId());
        } else {
            addInnerCommander(this.playerOathbreakers, player.getId(), card.getId());
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
            Set<UUID> commanders = this.playerOathbreakers.getOrDefault(player.getId(), new HashSet<>());
            Set<UUID> spells = this.playerSignatureSpells.getOrDefault(player.getId(), new HashSet<>());
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
                        if (spells.contains(id)) {
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
