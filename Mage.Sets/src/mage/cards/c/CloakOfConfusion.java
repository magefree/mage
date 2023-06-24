package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CloakOfConfusion extends CardImpl {

    public CloakOfConfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature attacks and isn't blocked, you may have it assign no combat damage this turn. 
        // If you do, defending player discards a card at random.
        this.addAbility(new CloakOfConfusionTriggeredAbility());
    }

    private CloakOfConfusion(final CloakOfConfusion card) {
        super(card);
    }

    @Override
    public CloakOfConfusion copy() {
        return new CloakOfConfusion(this);
    }
}

class CloakOfConfusionTriggeredAbility extends TriggeredAbilityImpl {

    CloakOfConfusionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DiscardTargetEffect(1, true), false);
        this.addEffect(new CloakOfConfusionEffect());
    }

    private CloakOfConfusionTriggeredAbility(final CloakOfConfusionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CloakOfConfusionTriggeredAbility copy() {
        return new CloakOfConfusionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UNBLOCKED_ATTACKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent aura = getSourcePermanentOrLKI(game);
        if (aura != null && event.getTargetId().equals(aura.getAttachedTo())) {
            this.getEffects().setTargetPointer(new FixedTarget(game.getCombat().getDefendingPlayerId(aura.getAttachedTo(), game)));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature attacks and isn't blocked, " +
                "you may have it assign no combat damage this turn. " +
                "If you do, defending player discards a card at random";
    }
}

class CloakOfConfusionEffect extends ReplacementEffectImpl {

    CloakOfConfusionEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
    }

    private CloakOfConfusionEffect(final CloakOfConfusionEffect effect) {
        super(effect);
    }

    @Override
    public CloakOfConfusionEffect copy() {
        return new CloakOfConfusionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!((DamageEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent aura = source.getSourcePermanentOrLKI(game);
        return aura != null && event.getSourceId().equals(aura.getAttachedTo());
    }
}
