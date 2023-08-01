
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
public class MomirDuel extends GameImpl {

    public MomirDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife, 60, 7);
    }

    public MomirDuel(final MomirDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new MomirDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
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
        state.getTurnMods().add(new TurnMod(startingPlayerId).withSkipStep(PhaseStep.DRAW));
    }

    @Override
    public MomirDuel copy() {
        return new MomirDuel(this);
    }

}
