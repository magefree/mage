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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.MultiplayerAttackOption;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.SetType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.game.match.MatchType;
import mage.game.permanent.token.EmptyToken;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;

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
    public Set<UUID> getOpponents(UUID playerId) {
        Set<UUID> opponents = new HashSet<>();
        for (UUID opponentId : this.getPlayer(playerId).getInRange()) {
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

    public MomirEmblem() {
        setName("Emblem Momir Vig, Simic Visionary");
        setExpansionSetCodeForImage("DIS");
        // {X}, Discard a card: Put a token into play as a copy of a random creature card with converted mana cost X. Play this ability only any time you could play a sorcery and only once each turn.
        LimitedTimesPerTurnActivatedAbility ability = new LimitedTimesPerTurnActivatedAbility(Zone.COMMAND, new MomirEffect(), new VariableManaCost());
        ability.addCost(new DiscardCardCost());
        ability.setTiming(TimingRule.SORCERY);
        this.getAbilities().add(ability);

    }
}

class MomirEffect extends OneShotEffect {

    public MomirEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public MomirEffect(MomirEffect effect) {
        super(effect);
        staticText = "Put a token into play as a copy of a random creature card with converted mana cost X";
    }

    @Override
    public MomirEffect copy() {
        return new MomirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = source.getManaCostsToPay().getX();
        // should this be random across card names, or card printings?
        CardCriteria criteria = new CardCriteria().types(CardType.CREATURE).convertedManaCost(value);
        List<CardInfo> options = CardRepository.instance.findCards(criteria);
        if (options == null || options.isEmpty()) {
            game.informPlayers("No random creature card with converted mana cost of " + value + " was found.");
            return false;
        }
        EmptyToken token = new EmptyToken(); // search for a non custom set creature
        while (token.getName().isEmpty() && !options.isEmpty()) {
            int index = RandomUtil.nextInt(options.size());
            ExpansionSet expansionSet = Sets.findSet(options.get(index).getSetCode());
            if (expansionSet == null || expansionSet.getSetType() == SetType.CUSTOM_SET) {
                options.remove(index);
            } else {
                Card card = options.get(index).getCard();
                if (card != null) {
                    CardUtil.copyTo(token).from(card);
                } else {
                    options.remove(index);
                }
            }
        }
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId(), false, false);
        return true;
    }
}
