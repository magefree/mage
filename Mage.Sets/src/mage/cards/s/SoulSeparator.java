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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken2;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class SoulSeparator extends CardImpl {

    public SoulSeparator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {5}, {T}, Sacrifice Soul Separator: Exile target creature card from your graveyard.
        // Create a token that's a copy of that card except it's 1/1, it's a Spirit in addition to its other types, and it has flying.
        // Create a black Zombie creature token with power equal to that card's power and toughness equal that card's toughness.
        PutTokenOntoBattlefieldCopyTargetEffect copyEffect = new PutTokenOntoBattlefieldCopyTargetEffect(null, null, false, 1, false, false, null, 1, 1, true);
        copyEffect.setAdditionalSubType("Spirit");
        copyEffect.setText("Create a token that's a copy of that card except it's 1/1, it's a Spirit in addition to its other types, and it has flying.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, copyEffect, new ManaCostsImpl("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        ability.addEffect(new SoulSeparatorEffect());
        this.addAbility(ability);
    }

    public SoulSeparator(final SoulSeparator card) {
        super(card);
    }

    @Override
    public SoulSeparator copy() {
        return new SoulSeparator(this);
    }
}

class SoulSeparatorEffect extends OneShotEffect {

    public SoulSeparatorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a black Zombie creature token with power equal to that card's power and toughness equal that card's toughness";
    }

    public SoulSeparatorEffect(final SoulSeparatorEffect effect) {
        super(effect);
    }

    @Override
    public SoulSeparatorEffect copy() {
        return new SoulSeparatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card creatureCard = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (creatureCard != null && controller != null) {
            boolean result = false;
            if (game.getState().getZone(creatureCard.getId()) == Zone.GRAVEYARD) {
                result = controller.moveCardToExileWithInfo(creatureCard, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                ZombieToken2 token = new ZombieToken2(creatureCard.getPower().getValue(), creatureCard.getToughness().getValue());
                token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            }
            return result;
        }
        return false;
    }
}
