
package mage.game;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.command.emblems.MomirEmblem;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;
import mage.game.turn.TurnMod;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author nigelzor
 */
public class MomirGame extends GameImpl {

    private int numPlayers;

    public MomirGame(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife, 60, 7);
    }

    public MomirGame(final MomirGame game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new MomirFreeForAllType();
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new InfoEffect("Vanguard effects"));
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            if (player != null) {
                CardInfo cardInfo = CardRepository.instance.findCardWithPreferredSetAndNumber("Momir Vig, Simic Visionary", "DIS", "118");
                if (cardInfo == null) {
                    // how-to fix: make sure that a Momir Emblem and a source card uses same set (DIS - Dissension)
                    throw new IllegalStateException("Wrong code usage: momir card and emblem must exists in the same set (DIS)");
                }
                addEmblem(new MomirEmblem(), cardInfo.getCard(), playerId);
            }
        }
        getState().addAbility(ability, null);
        super.init(choosingPlayerId);
        state.getTurnMods().add(new TurnMod(startingPlayerId).withSkipStep(PhaseStep.DRAW));
    }

    @Override
    public MomirGame copy() {
        return new MomirGame(this);
    }

}
