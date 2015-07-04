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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public class CopperGnomes extends CardImpl {

    public CopperGnomes(UUID ownerId) {
        super(ownerId, 291, "Copper Gnomes", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.expansionSetCode = "USG";
        this.subtype.add("Gnome");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {4}, Sacrifice Copper Gnomes: You may put an artifact card from your hand onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutArtifactOnBattlefieldEffect(), new ManaCostsImpl("{4}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public CopperGnomes(final CopperGnomes card) {
        super(card);
    }

    @Override
    public CopperGnomes copy() {
        return new CopperGnomes(this);
    }
}

class PutArtifactOnBattlefieldEffect extends OneShotEffect {

    private static final String choiceText = "Put an artifact card from your hand onto the battlefield?";

    public PutArtifactOnBattlefieldEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "You may put an artifact card from your hand onto the battlefield";
    }

    public PutArtifactOnBattlefieldEffect(final PutArtifactOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public PutArtifactOnBattlefieldEffect copy() {
        return new PutArtifactOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Outcome.PutCardInPlay, choiceText, source, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(new FilterArtifactCard());
        if (player.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                player.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId());
                return true;
            }
        }
        return false;
    }
}