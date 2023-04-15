package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AssassinToken;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * If an effect creates a copy of one of the Assassin creature tokens, the copy
 * will also have the triggered ability.
 * <p>
 * Each Assassin token's triggered ability will trigger whenever it deals combat
 * damage to any player, including you.
 *
 * @author LevelX2
 */
public final class VraskaTheUnseen extends CardImpl {

    public VraskaTheUnseen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);

        this.setStartingLoyalty(5);

        // +1: Until your next turn, whenever a creature deals combat damage to Vraska the Unseen, destroy that creature.
        this.addAbility(new LoyaltyAbility(new VraskaTheUnseenGainAbilityEffect(new VraskaTheUnseenTriggeredAbility()), 1));

        // -3: Destroy target nonland permanent.
        LoyaltyAbility ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);

        // -7: Create three 1/1 black Assassin creature tokens with "Whenever this creature deals combat damage to a player, that player loses the game."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AssassinToken(), 3), -7));
    }

    private VraskaTheUnseen(final VraskaTheUnseen card) {
        super(card);
    }

    @Override
    public VraskaTheUnseen copy() {
        return new VraskaTheUnseen(this);
    }
}

class VraskaTheUnseenGainAbilityEffect extends ContinuousEffectImpl {

    protected Ability ability;

    public VraskaTheUnseenGainAbilityEffect(Ability ability) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        staticText = "Until your next turn, whenever a creature deals combat damage to {this}, destroy that creature";
    }

    public VraskaTheUnseenGainAbilityEffect(final VraskaTheUnseenGainAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public VraskaTheUnseenGainAbilityEffect copy() {
        return new VraskaTheUnseenGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return game.getTurnPhaseType() == TurnPhase.END && this.isYourNextTurn(game);
    }
}

class VraskaTheUnseenTriggeredAbility extends TriggeredAbilityImpl {

    public VraskaTheUnseenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
    }

    public VraskaTheUnseenTriggeredAbility(final VraskaTheUnseenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VraskaTheUnseenTriggeredAbility copy() {
        return new VraskaTheUnseenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedEvent) event).isCombatDamage() && getSourceId().equals(event.getTargetId())) {
            Permanent sourceOfDamage = game.getPermanent(event.getSourceId());
            if (sourceOfDamage != null && sourceOfDamage.isCreature(game)) {
                Effect effect = this.getEffects().get(0);
                effect.setTargetPointer(new FixedTarget(sourceOfDamage.getId(), game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Until your next turn, whenever a creature deals combat damage to {this}, destroy that creature";
    }

}
