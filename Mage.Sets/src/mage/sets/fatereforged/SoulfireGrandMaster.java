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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SoulfireGrandMaster extends CardImpl {

    private static final FilterObject filter = new FilterObject("instant and sorcery spells you control");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    public SoulfireGrandMaster(UUID ownerId) {
        super(ownerId, 27, "Soulfire Grand Master", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Human");
        this.subtype.add("Monk");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Instant and sorcery spells you control have lifelink.
        Effect effect = new GainAbilitySpellsEffect(LifelinkAbility.getInstance(), filter);
        effect.setText("Instant and sorcery spells you control have lifelink");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // {2}{U/R}{U/R}: The next time you cast an instant or sorcery spell from your hand this turn, put that card into your hand instead of your graveyard as it resolves.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoulfireGrandMasterCastFromHandReplacementEffect(), new ManaCostsImpl("{2}{U/R}{U/R}")));

    }

    public SoulfireGrandMaster(final SoulfireGrandMaster card) {
        super(card);
    }

    @Override
    public SoulfireGrandMaster copy() {
        return new SoulfireGrandMaster(this);
    }
}

class GainAbilitySpellsEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final FilterObject filter;

    public GainAbilitySpellsEffect(Ability ability, FilterObject filter) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.filter = filter;
        staticText = filter.getMessage() + " have " + ability.getRule();
    }

    public GainAbilitySpellsEffect(final GainAbilitySpellsEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter;
    }

    @Override
    public GainAbilitySpellsEffect copy() {
        return new GainAbilitySpellsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            for (Card card: game.getExile().getAllCards(game)) {
                if (card.getOwnerId().equals(source.getControllerId()) && filter.match(card, game)) {
                    game.getState().addOtherAbility(card, ability);
                }
            }
            for (Card card: player.getLibrary().getCards(game)) {
                if (filter.match(card, game)) {
                    game.getState().addOtherAbility(card, ability);
                }
            }
            for (Card card: player.getHand().getCards(game)) {
                if (filter.match(card, game)) {
                    game.getState().addOtherAbility(card, ability);
                }
            }
            for (Card card: player.getGraveyard().getCards(game)) {
                if (filter.match(card, game)) {
                    game.getState().addOtherAbility(card, ability);
                }
            }
            for (StackObject stackObject : game.getStack()) {
                if (stackObject.getControllerId().equals(source.getControllerId())) {                        
                    Card card = game.getCard(stackObject.getSourceId());
                    if (card != null && filter.match(card, game)) {
                        if (!card.getAbilities().contains(ability)) {
                            game.getState().addOtherAbility(card, ability);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class SoulfireGrandMasterCastFromHandReplacementEffect extends ReplacementEffectImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    private UUID spellId;

    SoulfireGrandMasterCastFromHandReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.ReturnToHand);
        this.spellId = null;
        this.staticText = "The next time you cast an instant or sorcery spell from your hand this turn, put that card into your hand instead of into your graveyard as it resolves";
    }

    SoulfireGrandMasterCastFromHandReplacementEffect(SoulfireGrandMasterCastFromHandReplacementEffect effect) {
        super(effect);
        this.spellId = effect.spellId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public SoulfireGrandMasterCastFromHandReplacementEffect copy() {
        return new SoulfireGrandMasterCastFromHandReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject mageObject = game.getObject(spellId);        
        if (mageObject == null || !(mageObject instanceof Spell) || ((Spell)mageObject).isCopiedSpell()) {
            return false;
        } else {
            Card sourceCard = game.getCard(spellId);
            if (sourceCard != null) {
                Player player = game.getPlayer(sourceCard.getOwnerId());
                if (player != null) {
                    player.moveCardToHandWithInfo(sourceCard, source.getSourceId(), game, Zone.STACK);
                    discard();
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        //Something hit the stack from the hand, see if its a spell with this ability.
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (spellId == null && // because this effect works only once, spellId has to be null here
                zEvent.getFromZone() == Zone.HAND &&
                zEvent.getToZone() == Zone.STACK &&
                event.getPlayerId().equals(source.getControllerId())) {
            MageObject object = game.getObject(event.getTargetId());
            if (object instanceof Card) {
                if (filter.match((Card)object, game)) {
                    this.spellId = event.getTargetId();
                }
            }
        } else {
            // the spell goes to graveyard now so move it to hand again
            if (zEvent.getFromZone() == Zone.STACK &&
                    zEvent.getToZone() == Zone.GRAVEYARD &&
                    event.getTargetId().equals(spellId)) {
                Spell spell = game.getStack().getSpell(spellId);               
                if (spell != null && !spell.isCountered()) {
                    return true;
                }
            }
        }
        return false;
    }

}
