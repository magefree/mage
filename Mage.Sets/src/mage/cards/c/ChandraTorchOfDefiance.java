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
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.emblems.ChandraTorchOfDefianceEmblem;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author fireshoes
 */
public class ChandraTorchOfDefiance extends CardImpl {

    public ChandraTorchOfDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");
        this.subtype.add("Chandra");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Exile the top card of your library. You may cast that card. If you don't, Chandra, Torch of Defiance deals 2 damage to each opponent.
        LoyaltyAbility ability = new LoyaltyAbility(new ChandraTorchOfDefianceEffect(), 1);
        this.addAbility(ability);

        // +1: Add {R}{R} to your mana pool.
        this.addAbility(new LoyaltyAbility(new BasicManaEffect(Mana.RedMana(2)), +1));

        // -3: Chandra, Torch of Defiance deals 4 damage to target creature.
        ability = new LoyaltyAbility(new DamageTargetEffect(4), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -7: You get an emblem with "Whenever you cast a spell, this emblem deals 5 damage to target creature or player."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ChandraTorchOfDefianceEmblem()), -7));
    }

    public ChandraTorchOfDefiance(final ChandraTorchOfDefiance card) {
        super(card);
    }

    @Override
    public ChandraTorchOfDefiance copy() {
        return new ChandraTorchOfDefiance(this);
    }
}

class ChandraTorchOfDefianceEffect extends OneShotEffect {

    public ChandraTorchOfDefianceEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile the top card of your library. You may cast that card. If you don't, {this} deals 2 damage to each opponent";
    }

    public ChandraTorchOfDefianceEffect(final ChandraTorchOfDefianceEffect effect) {
        super(effect);
    }

    @Override
    public ChandraTorchOfDefianceEffect copy() {
        return new ChandraTorchOfDefianceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null && controller.getLibrary().hasCards()) {
            Library library = controller.getLibrary();
            Card card = library.getFromTop(game);
            if (card != null) {
                boolean exiledCardWasCast = false;
                controller.moveCardToExileWithInfo(card, source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
                if (!card.getManaCost().isEmpty()) {
                    if (controller.chooseUse(Outcome.Benefit, "Cast the card? (You still pay the costs)", source, game) && !card.isLand()) {
                        exiledCardWasCast = controller.cast(card.getSpellAbility(), game, false);
                    }
                }
                if (!exiledCardWasCast) {
                    new DamagePlayersEffect(Outcome.Damage, new StaticValue(2), TargetController.OPPONENT).apply(game, source);
                }

            }
            return true;
        }
        return false;
    }
}
