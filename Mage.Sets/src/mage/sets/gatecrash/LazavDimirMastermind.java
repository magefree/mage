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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class LazavDimirMastermind extends CardImpl<LazavDimirMastermind> {

    public LazavDimirMastermind(UUID ownerId) {
        super(ownerId, 174, "Lazav, Dimir Mastermind", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{U}{U}{B}{B}");
        this.expansionSetCode = "GTC";
        this.supertype.add("Legendary");
        this.subtype.add("Shapeshifter");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Whenever a creature card is put into an opponent's graveyard from anywhere, you may have Lazav, Dimir Mastermind become a copy of that card except its name is still Lazav, Dimir Mastermind, it's legendary in addition to its other types, and it gains hexproof and this ability.
        this.addAbility(new CreatureCardPutOpponentGraveyardTriggeredAbility());
    }

    public LazavDimirMastermind(final LazavDimirMastermind card) {
        super(card);
    }

    @Override
    public LazavDimirMastermind copy() {
        return new LazavDimirMastermind(this);
    }
}

class CreatureCardPutOpponentGraveyardTriggeredAbility extends TriggeredAbilityImpl<CreatureCardPutOpponentGraveyardTriggeredAbility> {

    public CreatureCardPutOpponentGraveyardTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LazavDimirEffect(), true);
    }

    public CreatureCardPutOpponentGraveyardTriggeredAbility(final CreatureCardPutOpponentGraveyardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CreatureCardPutOpponentGraveyardTriggeredAbility copy() {
        return new CreatureCardPutOpponentGraveyardTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card == null) {
                return false;
            }
            if (game.getOpponents(controllerId).contains(event.getPlayerId())
                    && card.getCardType().contains(CardType.CREATURE)) {
                // store the card id to copy
                game.getState().setValue(new StringBuilder("CardToCopy").append(getSourceId().toString()).toString(), card.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature card is put into an opponent's graveyard from anywhere, you may have {this} become a copy of that card except its name is still {this}, it's legendary in addition to its other types, and it gains hexproof and this ability.";
    }
}

class LazavDimirEffect extends ContinuousEffectImpl<LazavDimirEffect> {

    protected UUID IdOfCopiedCard;
    protected Card cardToCopy;

    public LazavDimirEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.CopyEffects_1, Constants.SubLayer.NA, Constants.Outcome.BecomeCreature);
    }

    public LazavDimirEffect(final LazavDimirEffect effect) {
        super(effect);
        this.cardToCopy = effect.cardToCopy;
        this.IdOfCopiedCard = effect.IdOfCopiedCard;
    }

    @Override
    public LazavDimirEffect copy() {
        return new LazavDimirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(new StringBuilder("CardToCopy").append(source.getSourceId().toString()).toString());
        Card card = null;
        if (object instanceof UUID) {
            card = game.getCard((UUID) object);
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (card == null || permanent == null) {
            return false;
        }
        if (IdOfCopiedCard == null || !IdOfCopiedCard.equals((UUID) object)) {
            IdOfCopiedCard = (UUID) object;
            cardToCopy = card.copy();
            cardToCopy.assignNewId();
        }
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
        permanent.getSupertype().add("Legendary");
        for (String type : cardToCopy.getSupertype()) {
            if (!permanent.getSupertype().contains(type)) {
                permanent.getSupertype().add(type);
            }
        }
        permanent.removeAllAbilities(source.getSourceId(), game);
        permanent.addAbility(HexproofAbility.getInstance(), source.getSourceId(), game);
        permanent.addAbility(new CreatureCardPutOpponentGraveyardTriggeredAbility(), source.getSourceId(), game);

        for (Ability ability : cardToCopy.getAbilities()) {
            if (!permanent.getAbilities().contains(ability)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }
}
