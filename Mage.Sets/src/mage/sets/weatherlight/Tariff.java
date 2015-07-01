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
package mage.sets.weatherlight;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class Tariff extends CardImpl {

    public Tariff(UUID ownerId) {
        super(ownerId, 144, "Tariff", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{W}");
        this.expansionSetCode = "WTH";


        // Each player sacrifices the creature he or she controls with the highest converted mana cost unless he or she pays that creature's mana cost. If two or more creatures a player controls are tied for highest cost, that player chooses one.
        this.getSpellAbility().addEffect(new TariffEffect());
    }

    public Tariff(final Tariff card) {
        super(card);
    }

    @Override
    public Tariff copy() {
        return new Tariff(this);
    }
}

class TariffEffect extends OneShotEffect {
    
    public TariffEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Each player sacrifices the creature he or she controls with the highest converted mana cost unless he or she pays that creature's mana cost. If two or more creatures a player controls are tied for highest cost, that player chooses one.";
    }
    
    public TariffEffect(final TariffEffect effect) {
        super(effect);
    }

    @Override
    public TariffEffect copy() {
        return new TariffEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        PlayerList playerList = game.getPlayerList().copy();
        playerList.setCurrent(game.getActivePlayerId());
        Player player = game.getPlayer(game.getActivePlayerId());
        
        do {
            processPlayer(game, source, player);
            player = playerList.getNext(game);
        } while (!player.getId().equals(game.getActivePlayerId()));
        
        return true;
    }
    
    private void processPlayer(Game game, Ability source, Player player) {
        MageObject sourceObject = game.getObject(source.getSourceId());

        List<Permanent> creatures = getPermanentsWithTheHighestCMC(game, player.getId(), new FilterControlledCreaturePermanent());

        Permanent creatureToPayFor = chooseOnePermanent(game, player, creatures);

        if (creatureToPayFor != null) {
            ManaCost manaCost = CardUtil.removeVariableManaCost(creatureToPayFor.getManaCost());
            String message = new StringBuilder("Pay ").append(manaCost.getText()).append(" (otherwise sacrifice ")
                    .append(creatureToPayFor.getName()).append(")?").toString();
            if (player.chooseUse(Outcome.Benefit, message, source, game)) {
                if (manaCost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                    game.informPlayers(new StringBuilder(sourceObject != null ? sourceObject.getName() : "")
                            .append(": ").append(player.getLogName()).append(" has paid").toString());
                    return;
                }
            }

            game.informPlayers(new StringBuilder(sourceObject != null ? sourceObject.getName() : "")
                    .append(": ").append(player.getLogName()).append(" hasn't paid").toString());
            creatureToPayFor.sacrifice(source.getSourceId(), game);
        }
    }
    
    private List<Permanent> getPermanentsWithTheHighestCMC(Game game, UUID playerId, FilterPermanent filter) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, playerId, game);
        int highestCMC = -1;
        for (Permanent permanent : permanents) {
            if (highestCMC < permanent.getManaCost().convertedManaCost()) {
                highestCMC = permanent.getManaCost().convertedManaCost();
            }
        }
        List<Permanent> result = new ArrayList<>();
        for (Permanent permanent : permanents) {
            if (permanent.getManaCost().convertedManaCost() == highestCMC) {
                result.add(permanent);
            }
        }
        return result;
    }

    private Permanent chooseOnePermanent(Game game, Player player, List<Permanent> permanents) {
        Permanent permanent = null;
        if (permanents.size() == 1) {
            permanent = permanents.iterator().next();
        } else if (permanents.size() > 1) {
            Cards cards = new CardsImpl();
            for (Permanent card : permanents) {
                cards.add(card);
            }

            TargetCard targetCard = new TargetCard(Zone.BATTLEFIELD, new FilterCard());
            if (player.choose(Outcome.Benefit, cards, targetCard, game)) {
                permanent = game.getPermanent(targetCard.getFirstTarget());
            }
        }
        return permanent;
    }
    
}
