
package mage.game;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.CommanderReplacementEffect;
import mage.abilities.effects.common.cost.CommanderCostModification;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.*;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.watchers.common.CommanderInfoWatcher;

/**
 *
 * @author JRHerlehy
 */
public abstract class GameTinyLeadersImpl extends GameImpl {

    protected boolean alsoHand; // replace also commander going to library
    protected boolean alsoLibrary; // replace also commander going to library
    protected boolean startingPlayerSkipsDraw = true;

    public GameTinyLeadersImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
    }

    public GameTinyLeadersImpl(final GameTinyLeadersImpl game) {
        super(game);
        this.alsoHand = game.alsoHand;
        this.startingPlayerSkipsDraw = game.startingPlayerSkipsDraw;
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new InfoEffect("Commander effects"));
        //Move tiny leader to command zone
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            if (player != null) {
                Card commander = getCommanderCard(player.getMatchPlayer().getDeck().getName(), player.getId());
                if (commander != null) {
                    Set<Card> cards = new HashSet<>();
                    cards.add(commander);
                    this.loadCards(cards, playerId);
                    player.addCommanderId(commander.getId());
                    commander.moveToZone(Zone.COMMAND, null, this, true);
                    ability.addEffect(new CommanderReplacementEffect(commander.getId(), alsoHand, alsoLibrary));
                    ability.addEffect(new CommanderCostModification(commander.getId()));
                    // Commander rule #4 was removed Jan. 18, 2016
                    // ability.addEffect(new CommanderManaReplacementEffect(player.getId(), CardUtil.getColorIdentity(commander)));
                    getState().setValue(commander.getId() + "_castCount", 0);
                    CommanderInfoWatcher watcher = new CommanderInfoWatcher(commander.getId(), false);
                    getState().getWatchers().add(watcher);
                    watcher.addCardInfoToCommander(this);
                } else {
                    throw new UnknownError("Commander card could not be created. Name: [" + player.getMatchPlayer().getDeck().getName() + ']');
                }
            }

        }
        this.getState().addAbility(ability, null);
        super.init(choosingPlayerId);
        if (startingPlayerSkipsDraw) {
            state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
        }
    }

    /**
     * Name of Tiny Leader comes from the deck name (it's not in the sideboard)
     * Additionally, it was taken into account that WOTC had missed a few color
     * combinations when making Legendary Creatures at 3 CMC. There are two
     * Commanders available to use for the missing color identities: Sultai [UBG
     * 3/3] and Glass [colorless 3/3]
     *
     * @param commanderName
     * @param ownerId
     * @return
     */
    public static Card getCommanderCard(String commanderName, UUID ownerId) {
        Card commander = null;
        if (commanderName != null) {
            switch (commanderName) {
                case "Sultai":
                    commander = new DefaultCommander(ownerId, commanderName, "{U}{B}{G}");
                    break;
                case "Glass":
                    commander = new DefaultCommander(ownerId, commanderName, "{C}{C}{C}");
                    break;
                default:
                    CardInfo cardInfo = CardRepository.instance.findCard(commanderName);
                    if (cardInfo != null) {
                        commander = cardInfo.getCard();
                    }
            }
        }
        return commander;
    }

    public void setAlsoHand(boolean alsoHand) {
        this.alsoHand = alsoHand;
    }

    public void setAlsoLibrary(boolean alsoLibrary) {
        this.alsoLibrary = alsoLibrary;
    }
}

class DefaultCommander extends CardImpl {

    public DefaultCommander(UUID ownerId, String commanderName, String manaString) {
        super(ownerId, new CardSetInfo(commanderName, "", "999", Rarity.RARE), new CardType[]{CardType.CREATURE}, manaString);
        this.addSuperType(SuperType.LEGENDARY);

        if (manaString.contains("{G}")) {
            this.color.setGreen(true);
        }
        if (manaString.contains("{W}")) {
            this.color.setWhite(true);
        }
        if (manaString.contains("{U}")) {
            this.color.setBlue(true);
        }
        if (manaString.contains("{B}")) {
            this.color.setBlack(true);
        }
        if (manaString.contains("{R}")) {
            this.color.setRed(true);
        }
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

    }

    public DefaultCommander(final DefaultCommander card) {
        super(card);
    }

    @Override
    public DefaultCommander copy() {
        return new DefaultCommander(this);
    }
}
