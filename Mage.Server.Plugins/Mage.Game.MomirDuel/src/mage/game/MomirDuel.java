/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.*;
import mage.game.command.Emblem;
import mage.game.match.MatchType;
import mage.game.permanent.token.EmptyToken;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;

/**
 *
 * @author nigelzor
 */
public class MomirDuel extends GameImpl {

    public MomirDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
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
        // should this be random across card names, or card printings?
        Map<Integer, List<Card>> available = new HashMap<>();
        CardCriteria criteria = new CardCriteria().types(CardType.CREATURE);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);

        for (CardInfo card : cards) {
            List<Card> options = available.get(card.getConvertedManaCost());
            if (options == null) {
                options = new ArrayList<>();
                available.put(card.getConvertedManaCost(), options);
            }
            options.add(card.getCard());
        }

        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new InfoEffect("Vanguard effects"));
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            if (player != null) {
                addEmblem(new MomirEmblem(available), ability, playerId);
            }
        }
        getState().addAbility(ability, null);
        super.init(choosingPlayerId);
        state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
    }

    @Override
    public Set<UUID> getOpponents(UUID playerId) {
        Set<UUID> opponents = new HashSet<>();
        for (UUID opponentId: this.getPlayer(playerId).getInRange()) {
            if (!opponentId.equals(playerId)) {
                opponents.add(opponentId);
            }
        }
        return opponents;
    }

    @Override
    public boolean isOpponent(Player player, UUID playerToCheck) {
        return !player.getId().equals(playerToCheck);
    }

    @Override
    public MomirDuel copy() {
        return new MomirDuel(this);
    }

}

// faking Vanguard as an Emblem; need to come back to this and add a new type of CommandObject
class MomirEmblem extends Emblem {

    public MomirEmblem(Map<Integer, List<Card>> available) {
        setName("Momir Vig, Simic Visionary");

        // {X}, Discard a card: Put a token into play as a copy of a random creature card with converted mana cost X. Play this ability only any time you could play a sorcery and only once each turn.
        LimitedTimesPerTurnActivatedAbility ability = new LimitedTimesPerTurnActivatedAbility(Zone.COMMAND, new MomirEffect(available), new VariableManaCost());
        ability.addCost(new DiscardCardCost());
        ability.setTiming(TimingRule.SORCERY);
        this.getAbilities().add(ability);

    }
}

class MomirEffect extends OneShotEffect {

    private static final Random rnd = new Random();
    private final Map<Integer, List<Card>> available;

    public MomirEffect(Map<Integer, List<Card>> available) {
        super(Outcome.PutCreatureInPlay);
        this.available = available;
    }

    public MomirEffect(MomirEffect effect) {
        super(effect);
        this.available = effect.available;
        staticText = "Put a token into play as a copy of a random creature card with converted mana cost X";
    }

    @Override
    public MomirEffect copy() {
        return new MomirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = source.getManaCostsToPay().getX();
        List<Card> options = available.get(value);
        if (options != null && !options.isEmpty()) {
            Card card = options.get(rnd.nextInt(options.size()));
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(card);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId(), false, false);
        }
        return true;
    }
}