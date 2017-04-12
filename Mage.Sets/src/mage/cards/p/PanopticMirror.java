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
package mage.cards.p;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public class PanopticMirror extends CardImpl {

    public PanopticMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Imprint - {X}, {tap}: You may exile an instant or sorcery card with converted mana cost X from your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PanopticMirrorExileEffect(), new VariableManaCost());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // At the beginning of your upkeep, you may copy a card exiled with Panoptic Mirror. If you do, you may cast the copy without paying its mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PanopticMirrorCastEffect(), TargetController.YOU, true));
    }

    public PanopticMirror(final PanopticMirror card) {
        super(card);
    }

    @Override
    public PanopticMirror copy() {
        return new PanopticMirror(this);
    }
}

class PanopticMirrorExileEffect extends OneShotEffect {

    public PanopticMirrorExileEffect() {
        super(Outcome.Exile);
        this.staticText = "You may exile an instant or sorcery card with converted mana cost X from your hand";
    }

    public PanopticMirrorExileEffect(final PanopticMirrorExileEffect effect) {
        super(effect);
    }

    @Override
    public PanopticMirrorExileEffect copy() {
        return new PanopticMirrorExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getManaCostsToPay().getX();
        int count = source.getManaCostsToPay().getX();

        FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard("instant or sorcery card with converted mana cost equal to " + count);
        filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, count));
        String choiceText = "Exile a " + filter.getMessage() + " from your hand?";

        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().count(filter, game) == 0
                || !player.chooseUse(this.outcome, choiceText, source, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(this.outcome, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                card.moveToExile(CardUtil.getCardExileZoneId(game, source), "Panoptic Mirror", source.getSourceId(), game);
                Permanent PanopticMirror = game.getPermanent(source.getSourceId());
                if(PanopticMirror != null){
                    PanopticMirror.imprint(card.getId(), game);
                }
                return true;
            }
        }
        return false;
    }
}

class PanopticMirrorCastEffect extends OneShotEffect {

    public PanopticMirrorCastEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "you may copy a card exiled with Panoptic Mirror. If you do, you may cast the copy without paying its mana cost";
    }

    public PanopticMirrorCastEffect(final PanopticMirrorCastEffect effect) {
        super(effect);
    }

    @Override
    public PanopticMirrorCastEffect copy() {
        return new PanopticMirrorCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent PanopticMirror = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (PanopticMirror == null) {
            PanopticMirror = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (PanopticMirror != null && PanopticMirror.getImprinted() != null && !PanopticMirror.getImprinted().isEmpty() && controller != null) {
            CardsImpl cards = new CardsImpl();
            for(UUID uuid : PanopticMirror.getImprinted()){
                Card card = game.getCard(uuid);
                if(card != null){
                    if(card.isSplitCard()){
                        cards.add(((SplitCard)card).getLeftHalfCard());
                        cards.add(((SplitCard)card).getRightHalfCard());
                    }
                    else{
                        cards.add(card);
                    }
                }
            }
            Card cardToCopy;
            if(cards.size() == 1){
                cardToCopy = cards.getCards(game).iterator().next();
            }
            else{
                TargetCard target = new TargetCard(1, Zone.EXILED, new FilterCard("card to copy"));
                controller.choose(Outcome.Copy, cards, target, game);
                cardToCopy = cards.get(target.getFirstTarget(), game);
            }
            if(cardToCopy != null){
                Card copy = game.copyCard(cardToCopy, source, source.getControllerId());
                if (controller.chooseUse(outcome, "Cast the copied card without paying mana cost?", source, game)) {
                    return controller.cast(copy.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
