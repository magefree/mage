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
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
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
            MageObject object = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (object == null) {
                return false;
            }
            if (game.getOpponents(controllerId).contains(event.getPlayerId())
                    && object.getCardType().contains(CardType.CREATURE)
                    && (!(object instanceof PermanentToken))) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(object.getId()));
                }
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

    public LazavDimirEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.CopyEffects_1, Constants.SubLayer.NA, Constants.Outcome.BecomeCreature);
    }

    public LazavDimirEffect(final LazavDimirEffect effect) {
        super(effect);
    }

    @Override
    public LazavDimirEffect copy() {
        return new LazavDimirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (card == null || permanent == null) {
            return false;
        }
        permanent.getPower().setValue(card.getPower().getValue());
        permanent.getToughness().setValue(card.getToughness().getValue());
        permanent.getColor().setColor(card.getColor());
        permanent.getManaCost().clear();
        permanent.getManaCost().add(card.getManaCost());
        for (CardType type : card.getCardType()) {
            if (!permanent.getCardType().contains(type)) {
                permanent.getCardType().add(type);
            }
        }
        for (String type : card.getSubtype()) {
            if (!permanent.getSubtype().contains(type)) {
                permanent.getSubtype().add(type);
            }
        }
        for (String type : card.getSupertype()) {
            if (!permanent.getSupertype().contains(type)) {
                permanent.getSupertype().add(type);
            }
        }
        permanent.setExpansionSetCode(card.getExpansionSetCode());
        for (Ability ability : card.getAbilities()) {
            if (!permanent.getAbilities().contains(ability)) {
                permanent.addAbility(ability, source.getId(), game);
            }
        }
        return true;
    }
}