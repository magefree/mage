package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.SpecialAction;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateSpecialActionEffect;
import mage.abilities.effects.common.RemoveSpecialActionEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author emerald000
 */
public class LicidAbility extends ActivatedAbilityImpl {

    public LicidAbility(ManaCost activationCost, ManaCost specialActionCost) {
        super(Zone.BATTLEFIELD, new LicidEffect(specialActionCost), activationCost);
        this.addCost(new TapSourceCost());
        this.addTarget(new TargetCreaturePermanent());
    }

    public LicidAbility(LicidAbility ability) {
        super(ability);
    }

    @Override
    public LicidAbility copy() {
        return new LicidAbility(this);
    }
}

class LicidEffect extends OneShotEffect {

    private final ManaCost specialActionCost;

    LicidEffect(ManaCost specialActionCost) {
        super(Outcome.Neutral);
        this.specialActionCost = specialActionCost;
        this.staticText = "{this} loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay " + specialActionCost.getText() + " to end this effect";
    }

    LicidEffect(final LicidEffect effect) {
        super(effect);
        this.specialActionCost = effect.specialActionCost;
    }

    @Override
    public LicidEffect copy() {
        return new LicidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent licid = source.getSourcePermanentIfItStillExists(game);
        if (licid != null) {
            UUID messageId = UUID.randomUUID();
            LicidContinuousEffect effect = new LicidContinuousEffect(messageId);
            effect.setTargetPointer(new FixedTarget(licid, game));
            game.addEffect(effect, source);
            new AttachEffect(Outcome.Neutral).apply(game, source);
            SpecialAction specialAction = new LicidSpecialAction(this.specialActionCost, messageId, licid.getIdName());
            new CreateSpecialActionEffect(specialAction).apply(game, source);
            return true;
        }
        return false;
    }
}

class LicidContinuousEffect extends ContinuousEffectImpl {

    private final UUID messageId;

    LicidContinuousEffect(UUID messageId) {
        super(Duration.Custom, Outcome.Neutral);
        this.messageId = messageId;
        dependencyTypes.add(DependencyType.AuraAddingRemoving);
    }

    LicidContinuousEffect(final LicidContinuousEffect ability) {
        super(ability);
        this.messageId = ability.messageId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent licid = source.getSourcePermanentIfItStillExists(game);
        if (licid != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    licid.removeAllCardTypes(game);
                    licid.addCardType(game, CardType.ENCHANTMENT);
                    licid.removeAllSubTypes(game);
                    licid.addSubType(game, SubType.AURA);
                    break;
                case AbilityAddingRemovingEffects_6:
                    List<Ability> toRemove = new ArrayList<>();
                    for (Ability ability : licid.getAbilities(game)) {
                        for (Effect effect : ability.getEffects()) {
                            if (effect instanceof LicidEffect) {
                                toRemove.add(ability);
                                break;
                            }
                        }
                    }
                    licid.removeAbilities(toRemove, source.getSourceId(), game);

                    Target target = new TargetCreaturePermanent();
                    Ability ability = new EnchantAbility(target);
                    ability.setRuleAtTheTop(true);
                    licid.addAbility(ability, source.getSourceId(), game);
                    licid.getSpellAbility().getTargets().clear();
                    licid.getSpellAbility().getTargets().add(target);
            }
            return true;
        }
        discard();
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        Object object = game.getState().getValue(this.messageId.toString());
        return object != null;
    }

    @Override
    public LicidContinuousEffect copy() {
        return new LicidContinuousEffect(this);
    }
}

class LicidSpecialAction extends SpecialAction {

    LicidSpecialAction(ManaCost cost, UUID messageId, String licidName) {
        super();
        this.addCost(cost);
        this.addEffect(new LicidSpecialActionEffect(messageId, this.getId(), licidName));
    }

    LicidSpecialAction(final LicidSpecialAction ability) {
        super(ability);
    }

    @Override
    public LicidSpecialAction copy() {
        return new LicidSpecialAction(this);
    }
}

class LicidSpecialActionEffect extends OneShotEffect {

    private final UUID messageId;
    private final UUID generatingSpecialActionId;

    LicidSpecialActionEffect(UUID messageId, UUID generatingSpecialActionId, String licidName) {
        super(Outcome.Neutral);
        this.messageId = messageId;
        this.generatingSpecialActionId = generatingSpecialActionId;
        this.staticText = "End " + licidName + " Effect";
    }

    LicidSpecialActionEffect(final LicidSpecialActionEffect effect) {
        super(effect);
        this.messageId = effect.messageId;
        this.generatingSpecialActionId = effect.generatingSpecialActionId;
    }

    @Override
    public LicidSpecialActionEffect copy() {
        return new LicidSpecialActionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new RemoveSpecialActionEffect(this.generatingSpecialActionId).apply(game, source);
        game.getState().setValue(this.messageId.toString(), true);
        return true;
    }
}
