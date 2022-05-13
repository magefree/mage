
package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author NinthWorld
 */
public final class LaviniaAzoriusRenegade extends CardImpl {

    public LaviniaAzoriusRenegade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.addSuperType(SuperType.LEGENDARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each opponent can't cast noncreature spells with converted mana cost greater than the number of lands that player controls.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LaviniaAzoriusRenegadeReplacementEffect()));

        // Whenever an opponent casts a spell, if no mana was spent to cast it, counter that spell.
        this.addAbility(new LaviniaAzoriusRenegadeTriggeredAbility());
    }

    private LaviniaAzoriusRenegade(final LaviniaAzoriusRenegade card) {
        super(card);
    }

    @Override
    public LaviniaAzoriusRenegade copy() {
        return new LaviniaAzoriusRenegade(this);
    }
}

class LaviniaAzoriusRenegadeReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    LaviniaAzoriusRenegadeReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each opponent can't cast noncreature spells with mana value greater than the number of lands that player controls.";
    }

    LaviniaAzoriusRenegadeReplacementEffect(final LaviniaAzoriusRenegadeReplacementEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast noncreature spells with mana value greater than " + getLandCount(source, event, game) + " (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getPlayer(source.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            FilterCard filter = new FilterCard();
            filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
            filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, getLandCount(source, event, game)));

            Card card = game.getCard(event.getSourceId());
            return card != null && filter.match(card, game);
        }
        return false;
    }

    private int getLandCount(Ability source, GameEvent event, Game game) {
        int landCount = 0;
        UUID playerId = event.getPlayerId();
        if(playerId != null) {
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LAND, playerId, source, game);
            for (Permanent permanent : permanents) {
                if (permanent.isControlledBy(playerId)) {
                    landCount++;
                }
            }
        }
        return landCount;
    }

    @Override
    public LaviniaAzoriusRenegadeReplacementEffect copy() {
        return new LaviniaAzoriusRenegadeReplacementEffect(this);
    }
}


class LaviniaAzoriusRenegadeTriggeredAbility extends TriggeredAbilityImpl {

    public LaviniaAzoriusRenegadeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CounterTargetEffect(), false);
    }

    public LaviniaAzoriusRenegadeTriggeredAbility(final LaviniaAzoriusRenegadeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LaviniaAzoriusRenegadeTriggeredAbility copy() {
        return new LaviniaAzoriusRenegadeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getStackAbility().getManaCostsToPay().getUsedManaToPay().count() == 0) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell, if no mana was spent to cast it, counter that spell.";
    }
}