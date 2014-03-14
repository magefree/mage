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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class WhipOfErebos extends CardImpl<WhipOfErebos> {

    public WhipOfErebos(UUID ownerId) {
        super(ownerId, 110, "Whip of Erebos", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT, CardType.ARTIFACT}, "{2}{B}{B}");
        this.expansionSetCode = "THS";
        this.supertype.add("Legendary");

        this.color.setBlack(true);

        // Creatures you control have lifelink.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent("Creatures"))));
        // {2}{B}{B}, {T}: Return target creature card from your graveyard to the battlefield. 
        // It gains haste. Exile it at the beginning of the next end step.
        // If it would leave the battlefield, exile it instead of putting it anywhere else.
        // Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new WhipOfErebosEffect(), new ManaCostsImpl("{2}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard"), true));
        ability.addEffect(new WhipOfErebosReplacementEffect());
        this.addAbility(ability);
    }

    public WhipOfErebos(final WhipOfErebos card) {
        super(card);
    }

    @Override
    public WhipOfErebos copy() {
        return new WhipOfErebos(this);
    }
}

class WhipOfErebosEffect extends OneShotEffect<WhipOfErebosEffect> {

    public WhipOfErebosEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target creature card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step";
    }

    public WhipOfErebosEffect(final WhipOfErebosEffect effect) {
        super(effect);
    }

    @Override
    public WhipOfErebosEffect copy() {
        return new WhipOfErebosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            card.addAbility(HasteAbility.getInstance());
            if (controller.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId())) {
                // gains haste
                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
                // Exile at begin of next end step
                ExileTargetEffect exileEffect = new ExileTargetEffect(null, null, Zone.BATTLEFIELD);
                exileEffect.setTargetPointer(new FixedTarget(card.getId()));
                DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(exileEffect);
                delayedAbility.setSourceId(source.getSourceId());
                delayedAbility.setControllerId(source.getControllerId());
                game.addDelayedTriggeredAbility(delayedAbility);                
            }
            return true;
        }
        return false;
    }
}

class WhipOfErebosReplacementEffect extends ReplacementEffectImpl<WhipOfErebosReplacementEffect> {

    WhipOfErebosReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Tap);
        staticText = "If it would leave the battlefield, exile it instead of putting it anywhere else";
    }

    WhipOfErebosReplacementEffect(final WhipOfErebosReplacementEffect effect) {
        super(effect);
    }

    @Override
    public WhipOfErebosReplacementEffect copy() {
        return new WhipOfErebosReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {            
            controller.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, null);
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && event.getTargetId().equals(source.getFirstTarget())
                && ((ZoneChangeEvent) event).getFromZone().equals(Zone.BATTLEFIELD)
                && !((ZoneChangeEvent) event).getToZone().equals(Zone.EXILED)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
