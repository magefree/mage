package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import static mage.cards.l.LostInThought.keyString;

/**
 *
 * @author xenohedron
 */
public final class LostInThought extends CardImpl {

    // (Implemented using the logic from VolrathsCurse as a reference)

    static final String keyString = "_ignoreEffectForTurn";

    public LostInThought (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't attack or block, and its activated abilities can't be activated.
        // Its controller may exile three cards from their graveyard for that player to ignore this effect until end of turn.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new LostInThoughtRestrictionEffect());
        ability.addEffect(new LostInThoughtCantActivateAbilitiesEffect());
        this.addAbility(ability);
        this.addAbility(new LostInThoughtSpecialAction());
    }

    public LostInThought (final LostInThought card) {
        super(card);
    }

    @Override
    public LostInThought copy() {
        return new LostInThought(this);
    }

}

class LostInThoughtRestrictionEffect extends RestrictionEffect {

    public LostInThoughtRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "Enchanted creature can't attack or block";
    }

    public LostInThoughtRestrictionEffect(final LostInThoughtRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null
                && permanent.getId().equals(attachment.getAttachedTo())) {
            String key = source.getSourceId().toString() + attachment.getZoneChangeCounter(game) + LostInThought.keyString + game.getTurnNum() + permanent.getControllerId();
            return game.getState().getValue(key) == null;
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public LostInThoughtRestrictionEffect copy() {
        return new LostInThoughtRestrictionEffect(this);
    }
}

class LostInThoughtCantActivateAbilitiesEffect extends ContinuousRuleModifyingEffectImpl {

    public LostInThoughtCantActivateAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.UnboostCreature);
        staticText = ", and its activated abilities can't be activated";
    }

    public LostInThoughtCantActivateAbilitiesEffect(final LostInThoughtCantActivateAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public LostInThoughtCantActivateAbilitiesEffect copy() {
        return new LostInThoughtCantActivateAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            if (event.getSourceId().equals(enchantment.getAttachedTo())) {
                Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
                if (enchanted != null) {
                    String key = source.getSourceId().toString() + enchantment.getZoneChangeCounter(game) + LostInThought.keyString + game.getTurnNum() + enchanted.getControllerId();
                    return game.getState().getValue(key) == null;
                }
            }
        }
        return false;
    }
}

class LostInThoughtSpecialAction extends SpecialAction {

    public LostInThoughtSpecialAction() {
        super(Zone.BATTLEFIELD);
        this.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(3), ""));
        this.addEffect(new LostInThoughtIgnoreEffect());
        this.setMayActivate(TargetController.CONTROLLER_ATTACHED_TO);
    }

    public LostInThoughtSpecialAction(final LostInThoughtSpecialAction ability) {
        super(ability);
    }

    @Override
    public LostInThoughtSpecialAction copy() {
        return new LostInThoughtSpecialAction(this);
    }
}

class LostInThoughtIgnoreEffect extends OneShotEffect {

    public LostInThoughtIgnoreEffect() {
        super(Outcome.Benefit);
        this.staticText = "Its controller may exile three cards from their graveyard for that player to ignore this effect until end of turn";
    }

    public LostInThoughtIgnoreEffect(final LostInThoughtIgnoreEffect effect) {
        super(effect);
    }

    @Override
    public LostInThoughtIgnoreEffect copy() {
        return new LostInThoughtIgnoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String key = source.getSourceId().toString() + source.getSourceObjectZoneChangeCounter() + keyString + game.getTurnNum() + ((ActivatedAbilityImpl) source).getActivatorId();
        game.getState().setValue(key, true);
        return true;
    }
}
