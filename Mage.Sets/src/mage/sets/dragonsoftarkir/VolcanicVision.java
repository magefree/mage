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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.postresolve.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class VolcanicVision extends CardImpl {

    public VolcanicVision(UUID ownerId) {
        super(ownerId, 167, "Volcanic Vision", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");
        this.expansionSetCode = "DTK";

        // Return target instant or sorcery card from your graveyard to your hand. Volcanic Visions deals damage equal to that card's converted mana cost to each creature your opponents control. Exile Volcanic Vision.
        this.getSpellAbility().addEffect(new VolcanicVisionReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard")));
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public VolcanicVision(final VolcanicVision card) {
        super(card);
    }

    @Override
    public VolcanicVision copy() {
        return new VolcanicVision(this);
    }
}

class VolcanicVisionReturnToHandTargetEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public VolcanicVisionReturnToHandTargetEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return target instant or sorcery card from your graveyard to your hand. {this} deals damage equal to that card's converted mana cost to each creature your opponents control";
    }

    public VolcanicVisionReturnToHandTargetEffect(final VolcanicVisionReturnToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public VolcanicVisionReturnToHandTargetEffect copy() {
        return new VolcanicVisionReturnToHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            switch (game.getState().getZone(targetId)) {
                case GRAVEYARD:
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD);
                        int damage = card.getManaCost().convertedManaCost();
                        if (damage > 0) {
                            for(Permanent creature: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                                creature.damage(damage, source.getSourceId(), game, false, true);
                            }
                        }
                    }
                    break;
            }
        }
        return true;
    }

}
