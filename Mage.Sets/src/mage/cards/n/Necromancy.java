
package mage.cards.n;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificeIfCastAtInstantTimeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashSourceEffect;
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

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Necromancy extends CardImpl {

    public Necromancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // You may cast Necromancy as though it had flash. If you cast it any time a sorcery couldn't have been cast, the controller of the permanent it becomes sacrifices it at the beginning of the next cleanup step.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame)));
        this.addAbility(new SacrificeIfCastAtInstantTimeTriggeredAbility());

        // When Necromancy enters the battlefield, if it's on the battlefield, it becomes an Aura with "enchant creature put onto the battlefield with Necromancy."
        // Put target creature card from a graveyard onto the battlefield under your control and attach Necromancy to it.
        // When Necromancy leaves the battlefield, that creature's controller sacrifices it.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new NecromancyReAttachEffect(), false),
                SourceOnBattlefieldCondition.instance,
                "When {this} enters the battlefield, if it's on the battlefield,  it becomes an Aura with \"enchant creature put onto the battlefield with {this}.\" Put target creature card from a graveyard onto the battlefield under your control and attach {this} to it.");
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new NecromancyLeavesBattlefieldTriggeredEffect(), false));
    }

    private Necromancy(final Necromancy card) {
        super(card);
    }

    @Override
    public Necromancy copy() {
        return new Necromancy(this);
    }
}

class NecromancyReAttachEffect extends OneShotEffect {

    public NecromancyReAttachEffect() {
        super(Outcome.Benefit);
        this.staticText = "it becomes an Aura with \"enchant creature put onto the battlefield with {this}\"";
    }

    public NecromancyReAttachEffect(final NecromancyReAttachEffect effect) {
        super(effect);
    }

    @Override
    public NecromancyReAttachEffect copy() {
        return new NecromancyReAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        Card cardInGraveyard = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller != null && enchantment != null && cardInGraveyard != null) {
            controller.moveCards(cardInGraveyard, Zone.BATTLEFIELD, source, game);
            Permanent enchantedCreature = game.getPermanent(cardInGraveyard.getId());
            if (enchantedCreature != null) {
                enchantedCreature.addAttachment(enchantment.getId(), source, game);
                FilterCreaturePermanent filter = new FilterCreaturePermanent("enchant creature put onto the battlefield with " + enchantment.getIdName());
                filter.add(new PermanentIdPredicate(cardInGraveyard.getId()));
                Target target = new TargetCreaturePermanent(filter);
                target.addTarget(enchantedCreature.getId(), source, game);
                game.addEffect(new NecromancyChangeAbilityEffect(target), source);
            }
            return true;
        }

        return false;
    }
}

class NecromancyLeavesBattlefieldTriggeredEffect extends OneShotEffect {

    public NecromancyLeavesBattlefieldTriggeredEffect() {
        super(Outcome.Benefit);
        this.staticText = "enchanted creature's controller sacrifices it";
    }

    public NecromancyLeavesBattlefieldTriggeredEffect(final NecromancyLeavesBattlefieldTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public NecromancyLeavesBattlefieldTriggeredEffect copy() {
        return new NecromancyLeavesBattlefieldTriggeredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (sourcePermanent.getAttachedTo() != null) {
                Permanent attachedTo = game.getPermanent(sourcePermanent.getAttachedTo());
                if (attachedTo != null && attachedTo.getZoneChangeCounter(game) == sourcePermanent.getAttachedToZoneChangeCounter()) {
                    attachedTo.sacrifice(source, game);
                }
            }
            return true;
        }
        return false;
    }
}

class NecromancyChangeAbilityEffect extends ContinuousEffectImpl implements SourceEffect {

    private static final Ability newAbility = new EnchantAbility("creature put onto the battlefield with Necromancy");

    static {
        newAbility.setRuleAtTheTop(true);
    }

    Target target;

    public NecromancyChangeAbilityEffect(Target target) {
        super(Duration.Custom, Outcome.AddAbility);
        staticText = "it becomes an Aura with \"enchant creature put onto the battlefield with {this}\"";
        this.target = target;
        dependencyTypes.add(DependencyType.AuraAddingRemoving);
    }

    public NecromancyChangeAbilityEffect(final NecromancyChangeAbilityEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public NecromancyChangeAbilityEffect copy() {
        return new NecromancyChangeAbilityEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.AURA);
                    break;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(newAbility, source.getSourceId(), game);
                    permanent.getSpellAbility().getTargets().clear();
                    permanent.getSpellAbility().getTargets().add(target);
            }
            return true;
        }
        this.discard();
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

}
