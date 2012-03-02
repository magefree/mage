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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public class HavengulLich extends CardImpl<HavengulLich> {

    private static final FilterCard filter = new FilterCreatureCard("creature card in a graveyard");

    public HavengulLich(UUID ownerId) {
        super(ownerId, 139, "Havengul Lich", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Zombie");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {1}: You may cast target creature card in a graveyard this turn. When you cast that card this turn, Havengul Lich gains all activated abilities of that card until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HavengulLichPlayEffect(), new ManaCostsImpl("{1}"));
        ability.addEffect(new HavengulLichPlayedEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
        
    }

    public HavengulLich(final HavengulLich card) {
        super(card);
    }

    @Override
    public HavengulLich copy() {
        return new HavengulLich(this);
    }
}

//allow card in graveyard to be played
class HavengulLichPlayEffect extends AsThoughEffectImpl<HavengulLichPlayEffect> {

	public HavengulLichPlayEffect() {
		super(Constants.AsThoughEffectType.CAST, Constants.Duration.EndOfTurn, Constants.Outcome.Benefit);
		staticText = "You may cast target creature card in a graveyard this turn";
	}

	public HavengulLichPlayEffect(final HavengulLichPlayEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public HavengulLichPlayEffect copy() {
		return new HavengulLichPlayEffect(this);
	}

	@Override
	public boolean applies(UUID sourceId, Ability source, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null && game.getState().getZone(card.getId()) == Constants.Zone.GRAVEYARD) {
            if (targetPointer.getFirst(source).equals(card.getId()))
                return true;
        }
        return false;
	}

}

//create delayed triggered ability to watch for card being played
class HavengulLichPlayedEffect extends OneShotEffect<HavengulLichPlayedEffect> {

    public HavengulLichPlayedEffect() {
        super(Outcome.PutCreatureInPlay);
    }
    
    public HavengulLichPlayedEffect(final HavengulLichPlayedEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility ability = new HavengulLichDelayedTriggeredAbility(targetPointer.getFirst(source));
        ability.setSourceId(source.getSourceId());
        ability.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(ability);
		return true;
    }

    @Override
    public HavengulLichPlayedEffect copy() {
        return new HavengulLichPlayedEffect(this);
    }
    
}

// when card is played create continuous effect
class HavengulLichDelayedTriggeredAbility extends DelayedTriggeredAbility<HavengulLichDelayedTriggeredAbility> {

    private UUID cardId;
    
	public HavengulLichDelayedTriggeredAbility (UUID cardId) {
		super(new HavengulLichEffect(cardId), Duration.EndOfTurn);
        this.cardId = cardId;
	}

	public HavengulLichDelayedTriggeredAbility(HavengulLichDelayedTriggeredAbility ability) {
		super(ability);
        this.cardId = ability.cardId;
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getSourceId().equals(cardId)) {
            return true;
		}
		return false;
	}
    
	@Override
	public HavengulLichDelayedTriggeredAbility copy() {
		return new HavengulLichDelayedTriggeredAbility(this);
	}
}

// copy activated abilities of card
class HavengulLichEffect extends ContinuousEffectImpl<HavengulLichEffect> {

    private UUID cardId;

    public HavengulLichEffect(UUID cardId) {
		super(Duration.EndOfTurn, Constants.Layer.AbilityAddingRemovingEffects_6, Constants.SubLayer.NA, Constants.Outcome.AddAbility);
        this.cardId = cardId;
	}

	public HavengulLichEffect(final HavengulLichEffect effect) {
		super(effect);
        this.cardId = effect.cardId;
	}

	@Override
	public HavengulLichEffect copy() {
		return new HavengulLichEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Card card = game.getCard(cardId);
        if (permanent != null && card != null) {
            for (ActivatedAbility ability: card.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
                permanent.addAbility(ability, game);
            }
		}
		return false;
	}
}