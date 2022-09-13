package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class CircleOfConfinement extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature an opponent controls with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public CircleOfConfinement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // When Circle of Confinement enters the battlefield, exile target creature an opponent controls with mana value 3 or less until Circle of Confinement leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

        // Whenever an opponent casts a Vampire spell with the same name as a card exiled with Circle of Confinement, you gain 2 life.
        this.addAbility(new CircleOfConfinementTriggeredAbility());
    }

    private CircleOfConfinement(final CircleOfConfinement card) {
        super(card);
    }

    @Override
    public CircleOfConfinement copy() {
        return new CircleOfConfinement(this);
    }
}

class CircleOfConfinementTriggeredAbility extends TriggeredAbilityImpl {

    public CircleOfConfinementTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(2));
        setTriggerPhrase("Whenever an opponent casts a Vampire spell with the same name as a card exiled with {this}, ");
    }

    private CircleOfConfinementTriggeredAbility(final CircleOfConfinementTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CircleOfConfinementTriggeredAbility copy() {
        return new CircleOfConfinementTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Spell spell = game.getSpell(event.getTargetId());
            if (spell != null && spell.hasSubtype(SubType.VAMPIRE, game)) {
                ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, sourceId, game.getState().getZoneChangeCounter(sourceId)));
                if (exileZone != null) {
                    for (UUID cardId : exileZone) {
                        if (CardUtil.haveSameNames(spell, game.getCard(cardId))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
