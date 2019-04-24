
package mage.game;

import java.util.UUID;
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
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author nigelzor
 */
public class MomirGame extends GameImpl {

    private int numPlayers;

    public MomirGame(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
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
                CardInfo cardInfo = CardRepository.instance.findCard("Momir Vig, Simic Visionary");
                addEmblem(new MomirEmblem(), cardInfo.getCard(), playerId);
            }
        }
        getState().addAbility(ability, null);
        super.init(choosingPlayerId);
        state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
    }

    @Override
    public MomirGame copy() {
        return new MomirGame(this);
    }

}
