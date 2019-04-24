
package mage.cards.a;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AnimateDead extends CardImpl {

    public AnimateDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature card in a graveyard
        TargetCardInGraveyard auraTarget = new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard"));
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AnimateDeadAttachEffect(Outcome.PutCreatureInPlay));
        Ability enchantAbility = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(enchantAbility);
        // When Animate Dead enters the battlefield, if it's on the battlefield, it loses "enchant creature card in a graveyard"
        // and gains "enchant creature put onto the battlefield with Animate Dead." Return enchanted creature card to the battlefield
        // under your control and attach Animate Dead to it. When Animate Dead leaves the battlefield, that creature's controller sacrifices it.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new AnimateDeadReAttachEffect(), false),
                SourceOnBattlefieldCondition.instance,
                "When {this} enters the battlefield, if it's on the battlefield, it loses \"enchant creature card in a graveyard\" and gains \"enchant creature put onto the battlefield with {this}.\" Return enchanted creature card to the battlefield under your control and attach {this} to it.");
        ability.addEffect(new AnimateDeadChangeAbilityEffect());
        this.addAbility(ability);
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new AnimateDeadLeavesBattlefieldTriggeredEffect(), false));

        // Enchanted creature gets -1/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(-1, 0, Duration.WhileOnBattlefield)));

    }

    public AnimateDead(final AnimateDead card) {
        super(card);
    }

    @Override
    public AnimateDead copy() {
        return new AnimateDead(this);
    }
}

class AnimateDeadReAttachEffect extends OneShotEffect {

    public AnimateDeadReAttachEffect() {
        super(Outcome.Benefit);
        this.staticText = "return enchanted creature card to the battlefield under your control and attach {this} to it";
    }

    public AnimateDeadReAttachEffect(final AnimateDeadReAttachEffect effect) {
        super(effect);
    }

    @Override
    public AnimateDeadReAttachEffect copy() {
        return new AnimateDeadReAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());

        if (controller != null && enchantment != null) {
            Card cardInGraveyard = game.getCard(enchantment.getAttachedTo());
            if (cardInGraveyard == null) {
                return true;
            }

            // put card into play
            controller.moveCards(cardInGraveyard, Zone.BATTLEFIELD, source, game);
            Permanent enchantedCreature = game.getPermanent(cardInGraveyard.getId());

            FilterCreaturePermanent filter = new FilterCreaturePermanent("enchant creature put onto the battlefield with Animate Dead");
            filter.add(new PermanentIdPredicate(cardInGraveyard.getId()));
            Target target = new TargetCreaturePermanent(filter);
            //enchantAbility.setTargetName(target.getTargetName());
            if (enchantedCreature != null) {
                target.addTarget(enchantedCreature.getId(), source, game);
                enchantment.getSpellAbility().getTargets().clear();
                enchantment.getSpellAbility().getTargets().add(target);
                enchantedCreature.addAttachment(enchantment.getId(), game);
            }
            return true;
        }

        return false;
    }
}

class AnimateDeadLeavesBattlefieldTriggeredEffect extends OneShotEffect {

    public AnimateDeadLeavesBattlefieldTriggeredEffect() {
        super(Outcome.Benefit);
        this.staticText = "enchanted creature's controller sacrifices it";
    }

    public AnimateDeadLeavesBattlefieldTriggeredEffect(final AnimateDeadLeavesBattlefieldTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public AnimateDeadLeavesBattlefieldTriggeredEffect copy() {
        return new AnimateDeadLeavesBattlefieldTriggeredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (sourcePermanent.getAttachedTo() != null) {
                Permanent attachedTo = game.getPermanent(sourcePermanent.getAttachedTo());
                if (attachedTo != null && attachedTo.getZoneChangeCounter(game) == sourcePermanent.getAttachedToZoneChangeCounter()) {
                    attachedTo.sacrifice(source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }
}

class AnimateDeadAttachEffect extends OneShotEffect {

    public AnimateDeadAttachEffect(Outcome outcome) {
        super(outcome);
    }

    public AnimateDeadAttachEffect(Outcome outcome, String rule) {
        super(outcome);
        staticText = rule;
    }

    public AnimateDeadAttachEffect(final AnimateDeadAttachEffect effect) {
        super(effect);
    }

    @Override
    public AnimateDeadAttachEffect copy() {
        return new AnimateDeadAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null && game.getState().getZone(source.getFirstTarget()) == Zone.GRAVEYARD) {
            // Card have no attachedTo attribute yet so write ref only to enchantment now
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null) {
                enchantment.attachTo(card.getId(), game);
            }
            return true;
        }
        return false;
    }

}

class AnimateDeadChangeAbilityEffect extends ContinuousEffectImpl implements SourceEffect {

    private final static Ability newAbility = new EnchantAbility("creature put onto the battlefield with Animate Dead");

    static {
        newAbility.setRuleAtTheTop(true);
    }

    public AnimateDeadChangeAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it loses \"enchant creature card in a graveyard\" and gains \"enchant creature put onto the battlefield with Animate Dead\"";
    }

    public AnimateDeadChangeAbilityEffect(final AnimateDeadChangeAbilityEffect effect) {
        super(effect);
    }

    @Override
    public AnimateDeadChangeAbilityEffect copy() {
        return new AnimateDeadChangeAbilityEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent != null) {
            Ability abilityToRemove = null;
            for (Ability ability : permanent.getAbilities()) {
                if (ability instanceof EnchantAbility) {
                    abilityToRemove = ability;
                }
            }
            if (abilityToRemove != null) {
                permanent.getAbilities().remove(abilityToRemove);
            }
            permanent.addAbility(newAbility, source.getSourceId(), game);
            return true;
        }
        return false;
    }
}
