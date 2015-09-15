/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.visions;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextCleanupDelayedTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Necromancy extends CardImpl {

    public Necromancy(UUID ownerId) {
        super(ownerId, 14, "Necromancy", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.expansionSetCode = "VIS";

        // You may cast Necromancy as though it had flash. If you cast it any time a sorcery couldn't have been cast, the controller of the permanent it becomes sacrifices it at the beginning of the next cleanup step.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CastSourceAsThoughItHadFlashEffect(this, Duration.EndOfGame, true)));
        this.addAbility(new CastAtInstantTimeTriggeredAbility());

        // When Necromancy enters the battlefield, if it's on the battlefield, it becomes an Aura with "enchant creature put onto the battlefield with Necromancy."
        // Put target creature card from a graveyard onto the battlefield under your control and attach Necromancy to it.
        // When Necromancy leaves the battlefield, that creature's controller sacrifices it.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new NecromancyReAttachEffect(), false),
                SourceOnBattlefieldCondition.getInstance(),
                "When {this} enters the battlefield, if it's on the battlefield,  it becomes an Aura with \"enchant creature put onto the battlefield with {this}.\" Put target creature card from a graveyard onto the battlefield under your control and attach {this} to it.");
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new NecromancyLeavesBattlefieldTriggeredEffect(), false));
    }

    public Necromancy(final Necromancy card) {
        super(card);
    }

    @Override
    public Necromancy copy() {
        return new Necromancy(this);
    }
}

class CastSourceAsThoughItHadFlashEffect extends AsThoughEffectImpl {

    private final boolean sacrificeIfCastAsInstant;

    public CastSourceAsThoughItHadFlashEffect(Card card, Duration duration, boolean sacrificeIfCastAsInstant) {
        super(AsThoughEffectType.CAST_AS_INSTANT, duration, Outcome.Benefit);
        this.sacrificeIfCastAsInstant = sacrificeIfCastAsInstant;
        staticText = "You may cast {this} as though it had flash";
    }

    public CastSourceAsThoughItHadFlashEffect(final CastSourceAsThoughItHadFlashEffect effect) {
        super(effect);
        this.sacrificeIfCastAsInstant = effect.sacrificeIfCastAsInstant;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CastSourceAsThoughItHadFlashEffect copy() {
        return new CastSourceAsThoughItHadFlashEffect(this);
    }

    @Override
    public boolean applies(UUID affectedSpellId, Ability source, UUID affectedControllerId, Game game) {
        return source.getSourceId().equals(affectedSpellId);
    }

}

class CastAtInstantTimeTriggeredAbility extends TriggeredAbilityImpl {

    public CastAtInstantTimeTriggeredAbility() {
        super(Zone.STACK, new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextCleanupDelayedTriggeredAbility(new SacrificeSourceEffect())));
    }

    public CastAtInstantTimeTriggeredAbility(final CastAtInstantTimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CastAtInstantTimeTriggeredAbility copy() {
        return new CastAtInstantTimeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // The sacrifice occurs only if you cast it using its own ability. If you cast it using some other
        // effect (for instance, if it gained flash from Vedalken Orrery), then it won't be sacrificed.
        // CHECK
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.getSourceId().equals(getSourceId())) {
            return !(game.isMainPhase() && game.getActivePlayerId().equals(event.getPlayerId()) && game.getStack().size() == 1);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "If you cast it any time a sorcery couldn't have been cast, the controller of the permanent it becomes sacrifices it at the beginning of the next cleanup step.";
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
            // put card into play
            controller.putOntoBattlefieldWithInfo(cardInGraveyard, game, Zone.GRAVEYARD, source.getSourceId());
            Permanent enchantedCreature = game.getPermanent(cardInGraveyard.getId());

            if (enchantedCreature != null) {
                enchantedCreature.addAttachment(enchantment.getId(), game);
                FilterCreaturePermanent filter = new FilterCreaturePermanent("enchant creature put onto the battlefield with Necromancy");
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
                if (attachedTo != null) {
                    attachedTo.sacrifice(source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }
}

class NecromancyChangeAbilityEffect extends ContinuousEffectImpl implements SourceEffect {

    private final static Ability newAbility = new EnchantAbility("creature put onto the battlefield with Necromancy");

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
                    if (sublayer == SubLayer.NA) {
                        if (!permanent.getSubtype().contains("Aura")) {
                            permanent.getSubtype().add("Aura");
                        }
                    }
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        permanent.addAbility(newAbility, source.getSourceId(), game);
                        permanent.getSpellAbility().getTargets().clear();
                        permanent.getSpellAbility().getTargets().add(target);
                    }
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
        return Layer.AbilityAddingRemovingEffects_6.equals(layer) || Layer.TypeChangingEffects_4.equals(layer);
    }

}
