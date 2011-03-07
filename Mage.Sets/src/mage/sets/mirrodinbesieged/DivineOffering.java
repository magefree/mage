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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author matt.maurer
 */
public class DivineOffering extends CardImpl<DivineOffering> {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.getCardType().add(CardType.ARTIFACT);
    }

    public DivineOffering(UUID ownerId) {
        super(ownerId, 5, "Divine Offering", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "MBS";
        this.getColor().setWhite(true);

        Target target = new TargetPermanent(filter);
        target.setTargetName("artifact");
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DivineOfferingGainLifeEffect());
    }

    public DivineOffering(final DivineOffering card) {
        super(card);
    }

    @Override
    public DivineOffering copy() {
        return new DivineOffering(this);
    }
}

class DivineOfferingGainLifeEffect extends OneShotEffect<DivineOfferingGainLifeEffect> {

    DivineOfferingGainLifeEffect() {
        super(Outcome.DestroyPermanent);
    }

    DivineOfferingGainLifeEffect ( final DivineOfferingGainLifeEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = player.getGraveyard().get(source.getFirstTarget(), game);
            if (card == null) {
                card = game.getLastKnownInformation(source.getFirstTarget(), Zone.GRAVEYARD);
            }
            if (card != null) {
                player.gainLife(card.getManaCost().convertedManaCost(), game);
            }
        }
        return true;
    }

    @Override
    public String getText(Ability source) {
        return "You gain life equal to it's converted mana cost.";
    }

    @Override
    public DivineOfferingGainLifeEffect copy() {
        return new DivineOfferingGainLifeEffect(this);
    }
}
