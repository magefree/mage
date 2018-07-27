package mage.cards.n;

import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.constants.*;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZergToken;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author NinthWorld
 */
public final class NydusWorm extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("land you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public NydusWorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        
        this.subtype.add(SubType.AURA);

        // Enchant land you don't control
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted land becomes untapped, put a 1/1 green Zerg creature token onto the battlefield.
        this.addAbility(new NydusWormTriggeredAbility());
    }

    public NydusWorm(final NydusWorm card) {
        super(card);
    }

    @Override
    public NydusWorm copy() {
        return new NydusWorm(this);
    }
}

class NydusWormTriggeredAbility extends TriggeredAbilityImpl {

    public NydusWormTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ZergToken()), false);
    }

    public NydusWormTriggeredAbility(final NydusWormTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NydusWormTriggeredAbility copy() {
        return new NydusWormTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(sourceId);
        if (enchantment == null) {
            enchantment = (Permanent) game.getLastKnownInformation(sourceId, Zone.BATTLEFIELD);
        }
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted == null) {
                enchanted = (Permanent) game.getLastKnownInformation(sourceId, Zone.BATTLEFIELD);
            }
            if (enchanted != null) {
                return event.getTargetId().equals(enchanted.getId());
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted land becomes untapped, " + super.getRule();
    }
}