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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public class SummonersEgg extends CardImpl {

    public SummonersEgg(UUID ownerId) {
        super(ownerId, 157, "Summoner's Egg", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.expansionSetCode = "5DN";
        this.subtype.add("Construct");
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Imprint - When Summoner's Egg enters the battlefield, you may exile a card from your hand face down.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SummonersEggImprintEffect(), true, "<i>Imprint - </i>"));
        // When Summoner's Egg dies, turn the exiled card face up. If it's a creature card, put it onto the battlefield under your control.
        this.addAbility(new DiesTriggeredAbility(new SummonersEggPutOntoBattlefieldEffect()));
    }

    public SummonersEgg(final SummonersEgg card) {
        super(card);
    }

    @Override
    public SummonersEgg copy() {
        return new SummonersEgg(this);
    }
}

class SummonersEggImprintEffect extends OneShotEffect {

    public SummonersEggImprintEffect() {
        super(Outcome.Benefit);
        staticText = "exile a card from your hand face down";
    }

    public SummonersEggImprintEffect(SummonersEggImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null) {
            if (controller.getHand().size() > 0) {
                TargetCard target = new TargetCard(Zone.HAND, new FilterCard());
                if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                        && controller.choose(Outcome.Benefit, controller.getHand(), target, game)) {
                    Card card = controller.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        card.setFaceDown(true);
                        controller.moveCardToExileWithInfo(card, source.getSourceId(), sourcePermanent.getLogName() +" (Imprint)", source.getSourceId(), game, Zone.HAND);
                        Permanent permanent = game.getPermanent(source.getSourceId());
                        if (permanent != null) {
                            permanent.imprint(card.getId(), game);
                            permanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card]"));
                        }
                    }
                }
            }
            return true;
        }
        return false;
        
    }

    @Override
    public SummonersEggImprintEffect copy() {
        return new SummonersEggImprintEffect(this);
    }

}

class SummonersEggPutOntoBattlefieldEffect extends OneShotEffect {

    public SummonersEggPutOntoBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "turn the exiled card face up. If it's a creature card, put it onto the battlefield under your control";
    }

    public SummonersEggPutOntoBattlefieldEffect(final SummonersEggPutOntoBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public SummonersEggPutOntoBattlefieldEffect copy() {
        return new SummonersEggPutOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent SummonersEgg = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (SummonersEgg != null && SummonersEgg.getImprinted() != null && !SummonersEgg.getImprinted().isEmpty()) {
                Card imprintedCard = game.getCard(SummonersEgg.getImprinted().get(0));
                if (imprintedCard != null && game.getState().getZone(imprintedCard.getId()).equals(Zone.EXILED)) {
                    //turn the exiled card face up. 
                    imprintedCard.turnFaceUp(game, source.getControllerId());
                    //If it's a creature card, 
                    if(imprintedCard.getCardType().contains(CardType.CREATURE)){
                        //put it onto the battlefield under your control
                        imprintedCard.putOntoBattlefield(game, Zone.EXILED, source.getSourceId(), source.getControllerId());
                    }
                }
            }
            return true;
        }
        return false;
    }
}