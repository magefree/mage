package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.*;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.game.events.GameEvent;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

/**
 *
 * @author PurpleCrowbar
 */
public final class EndlessEvil extends CardImpl {

    public EndlessEvil(UUID ownerID, CardSetInfo setInfo) {
        super(ownerID, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your upkeep, create a token that’s a copy of enchanted creature, except the token is 1/1.
        TriggeredAbility CloneAbility = new BeginningOfUpkeepTriggeredAbility(
                new EndlessEvilCloneEffect(),
                TargetController.YOU,
                false
        );
        this.addAbility(CloneAbility);

        // When enchanted creature dies, if that creature was a Horror, return Endless Evil to its owner’s hand.
        this.addAbility(new EndlessEvilBounceAbility());
    }

    private EndlessEvil(final EndlessEvil card) {
        super(card);
    }

    @Override
    public EndlessEvil copy() {
        return new EndlessEvil(this);
    }
}

class EndlessEvilCloneEffect extends OneShotEffect {

    public EndlessEvilCloneEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of enchanted creature, except the token is 1/1";
    }

    public EndlessEvilCloneEffect(final EndlessEvilCloneEffect effect) {
        super(effect);
    }

    @Override
    public EndlessEvilCloneEffect copy() {
        return new EndlessEvilCloneEffect(this);
    }

    @Override
    public boolean apply (Game game, Ability source) {
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // In the case that Endless Evil is blinked
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment != null) {
            Permanent target = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
            if (target != null) {
                Effect effect = new CreateTokenCopyTargetEffect(null, null, false, 1, false,
                        false, null, 1, 1, false);
                effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
                return effect.apply(game, source);
            }
        }
        return false;
    }
}

class EndlessEvilBounceAbility extends TriggeredAbilityImpl {

    public EndlessEvilBounceAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(false, true));
    }

    public EndlessEvilBounceAbility(final EndlessEvilBounceAbility effect) {
        super(effect);
    }

    @Override
    public EndlessEvilBounceAbility copy() {
        return new EndlessEvilBounceAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            return permanent.getAttachments().contains(this.getSourceId()) && permanent.hasSubtype(SubType.HORROR, game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted creature dies, if that creature was a Horror, return {this} to its owner's hand.";
    }
}
