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
package mage.cards.g;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.filter.FilterStackObject;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author spjspj
 */
public class GuardianBeast extends CardImpl {

    private static final FilterObject filterAura = new FilterStackObject("auras");
    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("Noncreature artifacts");

    static {
        filterAura.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAura.add(new SubtypePredicate("Aura"));
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public GuardianBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add("Beast");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // As long as Guardian Beast is untapped, noncreature artifacts you control can't be enchanted, they're indestructible, and other players can't gain control of them. This effect doesn't remove Auras already attached to those artifacts.
        Effect effect = new ConditionalContinuousEffect(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter), new InvertCondition(SourceTappedCondition.instance), "noncreature artifacts you control can't be enchanted, they're indestructible, and other players can't gain control of them");
        GuardianBeastConditionalEffect effect2 = new GuardianBeastConditionalEffect(this.getId());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect2));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public GuardianBeast(final GuardianBeast card) {
        super(card);
    }

    @Override
    public GuardianBeast copy() {
        return new GuardianBeast(this);
    }
}

class GuardianBeastConditionalEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("Noncreature artifacts");
    private UUID guardianBeastId;

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public GuardianBeastConditionalEffect(UUID guardianBeastId) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Noncreature artifacts you control have they can't be enchanted, they're indestructible, and other players can't gain control of them";
        this.guardianBeastId = guardianBeastId;
    }

    public GuardianBeastConditionalEffect(final GuardianBeastConditionalEffect effect) {
        super(effect);
        this.guardianBeastId = effect.guardianBeastId;
    }

    @Override
    public GuardianBeastConditionalEffect copy() {
        return new GuardianBeastConditionalEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(event.getTargetId());
        Permanent guardianBeast = game.getPermanent(guardianBeastId);

        if (guardianBeast == null || guardianBeast.isTapped() || sourceObject == null || targetPermanent == null) {
            return false;
        }

        if (!Objects.equals(targetPermanent.getControllerId(), guardianBeast.getControllerId())) {
            return false;
        }

        StackObject spell = game.getStack().getStackObject(event.getSourceId());
        if (event.getType() == EventType.LOSE_CONTROL || event.getType() == EventType.ATTACH || event.getType() == EventType.TARGET && spell != null && spell.isEnchantment() && spell.getSubtype(game).contains("Aura")) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (perm != null && Objects.equals(perm.getId(), targetPermanent.getId()) && !perm.isCreature()) {
                    return true;
                }
            }
        }
        return false;
    }

}
