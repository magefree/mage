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

package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX
 */
public class RagDealer extends CardImpl<RagDealer> {

    protected static final FilterCard filter = new FilterCard("up to three target cards from a single graveyard");

    public RagDealer (UUID ownerId) {
        super(ownerId, 138, "Rag Dealer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Human");
        this.subtype.add("Rogue");
    this.color.setBlack(true);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        // {2}{B}, {T}: Exile up to three target cards from a single graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RagDealerExileEffect(), new ManaCostsImpl("{2}{B}"));
        ability.addCost(new TapSourceCost());
        TargetPlayer target = new TargetPlayer();
        target.setTargetName("player from which graveyard you want to exile the cards");
        ability.addTarget(target);
        ability.addTarget(new TargetCardInGraveyard(0,3,filter));
        this.addAbility(ability);
    }

    public RagDealer (final RagDealer card) {
        super(card);
    }

    @Override
    public RagDealer copy() {
        return new RagDealer(this);
    }

}

class RagDealerTargetCardsInGraveyard extends TargetCard<RagDealerTargetCardsInGraveyard> {

    public RagDealerTargetCardsInGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, Constants.Zone.GRAVEYARD, filter);
        this.targetName = "up to three target cards from a single graveyard";
    }

    public RagDealerTargetCardsInGraveyard(final RagDealerTargetCardsInGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayer(source.getTargets().getFirstTarget());
        if (player != null) {
            filter.getOwnerId().clear();
            filter.getOwnerId().add(player.getId());
            return super.canTarget(id, source, game);
        }
        return false;
    }


    @Override
    public RagDealerTargetCardsInGraveyard copy() {
        return new RagDealerTargetCardsInGraveyard(this);
    }
}

class RagDealerExileEffect extends OneShotEffect<RagDealerExileEffect> {

    public RagDealerExileEffect() {
            super(Outcome.Exile);
    }

    public RagDealerExileEffect(final RagDealerExileEffect effect) {
            super(effect);
    }

    @Override
    public RagDealerExileEffect copy() {
            return new RagDealerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target targetCards = source.getTargets().get(1);
        if (targetCards != null) {
            for (UUID targetID : targetCards.getTargets()) {
                Card card = game.getCard(targetID);
                if (card  != null) {
                       card.moveToExile(null, "", source.getId(), game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Exile " + mode.getTargets().get(1).getTargetName();
    }
}
