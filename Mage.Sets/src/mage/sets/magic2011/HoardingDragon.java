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

package mage.sets.magic2011;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;



/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HoardingDragon extends CardImpl {

    public HoardingDragon(UUID ownerId) {
        super(ownerId, 144, "Hoarding Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "M11";
        this.subtype.add("Dragon");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // When Hoarding Dragon enters the battlefield, you may search your library for an artifact card, exile it, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HoardingDragonEffect(this.getId()), true));

        // When Hoarding Dragon dies, you may put the exiled card into its owner's hand.
        this.addAbility(new DiesTriggeredAbility(new ReturnFromExileEffect(this.getId(), Zone.HAND), false));
    }

    public HoardingDragon(final HoardingDragon card) {
        super(card);
    }

    @Override
    public HoardingDragon copy() {
        return new HoardingDragon(this);
    }

}

class HoardingDragonEffect extends OneShotEffect {

    private final UUID exileId;

    public HoardingDragonEffect(UUID exileId) {
        super(Outcome.Exile);
        this.exileId = exileId;
        this.staticText = "you may search your library for an artifact card, exile it, then shuffle your library";
    }

    public HoardingDragonEffect(final HoardingDragonEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(new FilterArtifactCard());
            if (controller.searchLibrary(target, game)) {
                if (target.getTargets().size() > 0) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, exileId, "Hoarding Dragon", source.getSourceId(), game, Zone.LIBRARY, true);
                    }
                }
            }
            controller.shuffleLibrary(game);
            return true;
        }       
        return false;
    }

    @Override
    public HoardingDragonEffect copy() {
        return new HoardingDragonEffect(this);
    }

}
