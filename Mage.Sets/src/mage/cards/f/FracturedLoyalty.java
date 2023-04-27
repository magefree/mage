package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J (significantly based on wetterlicht)
 */
public final class FracturedLoyalty extends CardImpl {

    public FracturedLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature becomes the target of a spell or ability, that spell or ability's controller gains control of that creature.
        this.addAbility(new FracturedLoyaltyTriggeredAbility());
    }

    private FracturedLoyalty(final FracturedLoyalty card) {
        super(card);
    }

    @Override
    public FracturedLoyalty copy() {
        return new FracturedLoyalty(this);
    }

    private static class FracturedLoyaltyEffect extends OneShotEffect {

        public FracturedLoyaltyEffect() {
            super(Outcome.GainControl);
            this.staticText = "that spell or ability's controller gains control of that creature";
        }

        private FracturedLoyaltyEffect(FracturedLoyaltyEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            // In the case that Fractured Loyalty is blinked
            Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            if (enchantment == null) {
                // It was not blinked, use the standard method
                enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            }
            if (enchantment != null) {
                Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
                if (enchantedCreature != null) {
                    Player controller = game.getPlayer(enchantedCreature.getControllerId());
                    if (enchantment.getAttachedTo() != null) {
                        if (controller != null && !enchantedCreature.isControlledBy(this.getTargetPointer().getFirst(game, source))) {
                            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, this.getTargetPointer().getFirst(game, source));
                            effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
                            game.addEffect(effect, source);
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public Effect copy() {
            return new FracturedLoyaltyEffect(this);
        }

    }

    class FracturedLoyaltyTriggeredAbility extends TriggeredAbilityImpl {

        public FracturedLoyaltyTriggeredAbility() {
            super(Zone.BATTLEFIELD, new FracturedLoyaltyEffect(), false);
        }

        public FracturedLoyaltyTriggeredAbility(final FracturedLoyaltyTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public FracturedLoyaltyTriggeredAbility copy() {
            return new FracturedLoyaltyTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.TARGETED;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            Permanent enchantment = game.getPermanentOrLKIBattlefield(this.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
                if (enchantedCreature != null && event.getTargetId().equals(enchantment.getAttachedTo())) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever enchanted creature becomes the target of a spell or ability, that spell or ability's controller gains control of that creature.";
        }
    }
}
