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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author emerald000
 */
public class TheMimeoplasm extends CardImpl {

    public TheMimeoplasm(UUID ownerId) {
        super(ownerId, 210, "The Mimeoplasm", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{B}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Ooze");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As The Mimeoplasm enters the battlefield, you may exile two creature cards from graveyards. If you do, it enters the battlefield as a copy of one of those cards with a number of additional +1/+1 counters on it equal to the power of the other card.
        this.addAbility(new AsEntersBattlefieldAbility(new TheMimeoplasmEffect(), "you may exile two creature cards from graveyards. If you do, it enters the battlefield as a copy of one of those cards with a number of additional +1/+1 counters on it equal to the power of the other card"));
    }

    public TheMimeoplasm(final TheMimeoplasm card) {
        super(card);
    }

    @Override
    public TheMimeoplasm copy() {
        return new TheMimeoplasm(this);
    }
}

class TheMimeoplasmEffect extends ContinuousEffectImpl {
    
    protected Card cardToCopy;
    protected boolean firstApply = true;
    
    TheMimeoplasmEffect() {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.NA, Outcome.Copy);
    }
    
    TheMimeoplasmEffect(final TheMimeoplasmEffect effect) {
        super(effect);
        this.cardToCopy = effect.cardToCopy;
        this.firstApply = effect.firstApply;
    }

    @Override
    public TheMimeoplasmEffect copy() {
        return new TheMimeoplasmEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            //Choose the creatures to exile if it is applied for the first time.
            if (firstApply) {
                this.firstApply = false;
                if (new CardsInAllGraveyardsCount(new FilterCreatureCard()).calculate(game, source) >= 2) {
                    if (player.chooseUse(Outcome.Benefit, "Do you want to exile two creature cards from graveyards?", game)) {
                        TargetCardInGraveyard targetCopy = new TargetCardInGraveyard(new FilterCreatureCard("creature card to become a copy of"));
                        TargetCardInGraveyard targetCounters = new TargetCardInGraveyard(new FilterCreatureCard("creature card for additional +1/+1 counters"));
                        if (player.choose(Outcome.Copy, targetCopy, source.getSourceId(), game)) {
                            this.cardToCopy = game.getCard(targetCopy.getFirstTarget());
                            if (cardToCopy != null) {
                                player.moveCardToExileWithInfo(cardToCopy, null, "", source.getSourceId(), game, Zone.GRAVEYARD);
                                if (player.choose(Outcome.Copy, targetCounters, source.getSourceId(), game)) {
                                    Card cardForCounters = game.getCard(targetCounters.getFirstTarget());
                                    if (cardForCounters != null) {
                                        player.moveCardToExileWithInfo(cardForCounters, null, "", source.getSourceId(), game, Zone.GRAVEYARD);
                                        permanent.addCounters(CounterType.P1P1.createInstance(cardForCounters.getPower().getValue()), game);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //Apply copy effect
            if (cardToCopy != null) {
                permanent.setName(cardToCopy.getName());
                permanent.getPower().setValue(cardToCopy.getPower().getValue());
                permanent.getToughness().setValue(cardToCopy.getToughness().getValue());
                permanent.getColor().setColor(cardToCopy.getColor());
                permanent.getManaCost().clear();
                permanent.getManaCost().add(cardToCopy.getManaCost());
                permanent.getCardType().clear();
                for (CardType type : cardToCopy.getCardType()) {
                    if (!permanent.getCardType().contains(type)) {
                        permanent.getCardType().add(type);
                    }
                }
                permanent.getSubtype().clear();
                for (String type : cardToCopy.getSubtype()) {
                    if (!permanent.getSubtype().contains(type)) {
                        permanent.getSubtype().add(type);
                    }
                }
                permanent.getSupertype().clear();
                for (String type : cardToCopy.getSupertype()) {
                    if (!permanent.getSupertype().contains(type)) {
                        permanent.getSupertype().add(type);
                    }
                }
                permanent.removeAllAbilities(source.getSourceId(), game);
                for (Ability ability : cardToCopy.getAbilities()) {
                    if (!permanent.getAbilities().contains(ability)) {
                        permanent.addAbility(ability, source.getSourceId(), game);
                    }
                }
                permanent.setCardNumber(cardToCopy.getCardNumber());
                permanent.setExpansionSetCode(cardToCopy.getExpansionSetCode());
                permanent.setCopy(true);
                return true;
            }
        }
        return false;
    }
}