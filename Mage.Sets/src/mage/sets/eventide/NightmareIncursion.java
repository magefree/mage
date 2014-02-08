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
package mage.sets.eventide;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public class NightmareIncursion extends CardImpl<NightmareIncursion> {

    public NightmareIncursion(UUID ownerId) {
        super(ownerId, 40, "Nightmare Incursion", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{B}");
        this.expansionSetCode = "EVE";

        this.color.setBlack(true);

        // Search target player's library for up to X cards, where X is the number of Swamps you control, and exile them. Then that player shuffles his or her library.
        Effect effect = new NightmareIncursionEffect();
        this.getSpellAbility().addTarget(new TargetPlayer(true));
        this.getSpellAbility().addEffect(effect);

    }

    public NightmareIncursion(final NightmareIncursion card) {
        super(card);
    }

    @Override
    public NightmareIncursion copy() {
        return new NightmareIncursion(this);
    }
}

class NightmareIncursionEffect extends OneShotEffect<NightmareIncursionEffect> {

    private static final  FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate("Swamp"));
    }

    boolean exiled = false;

    public NightmareIncursionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search target player's library for up to X cards, where X is the number of Swamps you control, and exile them. Then that player shuffles his or her library";
    }

    public NightmareIncursionEffect(final NightmareIncursionEffect effect) {
        super(effect);
    }

    @Override
    public NightmareIncursionEffect copy() {
        return new NightmareIncursionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (controller != null && targetPlayer != null) {
            int amount = new PermanentsOnBattlefieldCount(filter).calculate(game, source);
            TargetCardInLibrary target = new TargetCardInLibrary(0, amount, new FilterCard());
            if (controller.searchLibrary(target, game, targetPlayer.getId())) {
                List<UUID> targetId = target.getTargets();
                for (UUID targetCard : targetId) {
                    Card card = targetPlayer.getLibrary().remove(targetCard, game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, Zone.LIBRARY);
                        result = true;
                    }
                }
            }
        }
        if (targetPlayer != null) {
            targetPlayer.shuffleLibrary(game);
        }
        return result;
    }
}
