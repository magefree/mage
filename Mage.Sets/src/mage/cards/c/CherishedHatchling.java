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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class CherishedHatchling extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("Dinosaur spells");

    static {
        filterCard.add(new SubtypePredicate(SubType.DINOSAUR));
    }

    public CherishedHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Cherished Hatchling dies, you may cast Dinosaur spells this turn as though they had flash, and whenever you cast a Dinosaur spell this turn, it gains "When this creature enters the battlefield, you may have it fight another target creature."
        Ability ability = new DiesTriggeredAbility(new CastAsThoughItHadFlashAllEffect(Duration.EndOfTurn, filterCard, false));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CherishedHatchlingTriggeredAbility()));
        this.addAbility(ability);
    }

    public CherishedHatchling(final CherishedHatchling card) {
        super(card);
    }

    @Override
    public CherishedHatchling copy() {
        return new CherishedHatchling(this);
    }
}

class CherishedHatchlingTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public CherishedHatchlingTriggeredAbility() {
        super(getEffectToAdd(), Duration.EndOfTurn, true);
    }

    private static Effect getEffectToAdd() {
        Ability abilityToAdd = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect().setText("you may have it fight another target creature"), true);
        abilityToAdd.addTarget(new TargetCreaturePermanent(filter));
        Effect effect = new GainAbilityTargetEffect(abilityToAdd, Duration.EndOfTurn,
                "it gains \"When this creature enters the battlefield, you may have it fight another target creature.\"", true);
        return effect;
    }

    private CherishedHatchlingTriggeredAbility(final CherishedHatchlingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CherishedHatchlingTriggeredAbility copy() {
        return new CherishedHatchlingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isCreature() && spell.hasSubtype(SubType.DINOSAUR, game)) {
                getEffects().setTargetPointer(new FixedTarget(spell.getSourceId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "and whenever you cast a Dinosaur spell this turn, " + super.getRule();
    }
}

//class CherishedHatchlingGainAbilityEffect extends ContinuousEffectImpl {
//
//    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
//
//    static {
//        filter.add(new AnotherPredicate());
//    }
//    private Ability abilityToAdd = null;
//    private Card relatedCard = null;
//
//    public CherishedHatchlingGainAbilityEffect() {
//        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
//        staticText = "it gains \"When this creature enters the battlefield, you may have it fight another target creature.\"";
//    }
//
//    public CherishedHatchlingGainAbilityEffect(final CherishedHatchlingGainAbilityEffect effect) {
//        super(effect);
//        this.abilityToAdd = effect.abilityToAdd;
//        this.relatedCard = effect.relatedCard;
//    }
//
//    @Override
//    public CherishedHatchlingGainAbilityEffect copy() {
//        return new CherishedHatchlingGainAbilityEffect(this);
//    }
//
//    @Override
//    public boolean apply(Game game, Ability source) {
//        if (relatedCard == null) {
//            Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
//            if (spell != null) {
//                relatedCard = game.getCard(spell.getSourceId());
//                Effect effect = new FightTargetSourceEffect();
//                effect.setText("you may have it fight another target creature");
//                abilityToAdd = new EntersBattlefieldTriggeredAbility(effect, true);
//                abilityToAdd.addTarget(new TargetCreaturePermanent(filter));
//            }
//        }
//        if (relatedCard != null) {
//            game.getState().addOtherAbility(relatedCard, abilityToAdd, false);
//        }
//        return true;
//    }
//}
