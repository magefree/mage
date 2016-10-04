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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class DeceiverOfForm extends CardImpl {

    public DeceiverOfForm(UUID ownerId) {
        super(ownerId, 1, "Deceiver of Form", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{C}");
        this.expansionSetCode = "OGW";
        this.subtype.add("Eldrazi");
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // At the beginning of combat on your turn, reveal the top card of your library.
        // If a creature card is revealed this way, you may have creatures you control other than Deceiver of Form becomes copies of that card until end of turn.
        // You may put that card on the bottom of your library.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DeceiverOfFormEffect(), TargetController.YOU, false));
    }

    public DeceiverOfForm(final DeceiverOfForm card) {
        super(card);
    }

    @Override
    public DeceiverOfForm copy() {
        return new DeceiverOfForm(this);
    }
}

class DeceiverOfFormEffect extends OneShotEffect {

    public DeceiverOfFormEffect() {
        super(Outcome.Copy);
        this.staticText = "reveal the top card of your library. If a creature card is revealed this way, you may have creatures you control other than Deceiver of Form becomes copies of that card until end of turn. You may put that card on the bottom of your library";
    }

    public DeceiverOfFormEffect(final DeceiverOfFormEffect effect) {
        super(effect);
    }

    @Override
    public DeceiverOfFormEffect copy() {
        return new DeceiverOfFormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl(card);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    if (controller.chooseUse(outcome, "Let creatures you control other than "
                            + sourceObject.getLogName() + " becomes copies of " + card.getLogName() + " until end of turn?", source, game)) {
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), controller.getId(), game)) {
                            if (!permanent.getId().equals(sourceObject.getId())) {
                                ContinuousEffect effect = new DeceiverOfFormCopyEffect(card);
                                effect.setTargetPointer(new FixedTarget(permanent, game));
                                game.addEffect(effect, source);
                            }
                        }
                    }
                }
                if (controller.chooseUse(outcome, "Move " + card.getLogName() + " to the bottom of your library?", source, game)) {
                    controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, false, true);
                }
            }
            return true;
        }
        return false;
    }
}

class DeceiverOfFormCopyEffect extends ContinuousEffectImpl {

    private final Card card;

    public DeceiverOfFormCopyEffect(Card card) {
        super(Duration.EndOfTurn, Layer.CopyEffects_1, SubLayer.NA, Outcome.BecomeCreature);
        this.card = card;
        staticText = "becomes copies of that card until end of turn";
    }

    public DeceiverOfFormCopyEffect(final DeceiverOfFormCopyEffect effect) {
        super(effect);
        this.card = effect.card;
    }

    @Override
    public DeceiverOfFormCopyEffect copy() {
        return new DeceiverOfFormCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (card == null || permanent == null) {
            discard();
            return false;
        }
        permanent.setName(card.getName());
        permanent.getPower().setValue(card.getPower().getValue());
        permanent.getToughness().setValue(card.getToughness().getValue());
        permanent.getColor(game).setColor(card.getColor(game));
        permanent.getManaCost().clear();
        permanent.getManaCost().add(card.getManaCost());
        permanent.getCardType().clear();
        for (CardType type : card.getCardType()) {
            if (!permanent.getCardType().contains(type)) {
                permanent.getCardType().add(type);
            }
        }
        permanent.getSubtype(game).clear();
        for (String type : card.getSubtype(game)) {
            if (!permanent.getSubtype(game).contains(type)) {
                permanent.getSubtype(game).add(type);
            }
        }
        permanent.getSupertype().clear();
        for (String type : card.getSupertype()) {
            if (!permanent.getSupertype().contains(type)) {
                permanent.getSupertype().add(type);
            }
        }
        permanent.removeAllAbilities(source.getSourceId(), game);

        for (Ability ability : card.getAbilities()) {
            if (!permanent.getAbilities().contains(ability)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }
}
