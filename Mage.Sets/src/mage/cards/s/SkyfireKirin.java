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
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class SkyfireKirin extends CardImpl {

    private final UUID originalId;

    public SkyfireKirin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Kirin");
        this.subtype.add("Spirit");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a Spirit or Arcane spell, you may gain control of target creature with that spell's converted mana cost until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, new SkyfireKirinEffect(), new FilterSpiritOrArcaneCard(), true, true);
        ability.addTarget(new TargetCreaturePermanent());
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            Spell spell = game.getStack().getSpell(ability.getEffects().get(0).getTargetPointer().getFirst(game, ability));
            if (spell != null) {
                int cmc = spell.getConvertedManaCost();
                ability.getTargets().clear();
                FilterPermanent filter = new FilterCreaturePermanent(new StringBuilder("creature with converted mana costs of ").append(cmc).toString());
                filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, cmc));
                Target target = new TargetPermanent(filter);
                ability.addTarget(target);
            }
        }
    }

    public SkyfireKirin(final SkyfireKirin card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public SkyfireKirin copy() {
        return new SkyfireKirin(this);
    }
}

class SkyfireKirinEffect extends OneShotEffect {

    public SkyfireKirinEffect() {
        super(Outcome.Detriment);
        this.staticText = "you may gain control of target creature with that spell's converted mana cost until end of turn";
    }

    public SkyfireKirinEffect(final SkyfireKirinEffect effect) {
        super(effect);
    }

    @Override
    public SkyfireKirinEffect copy() {
        return new SkyfireKirinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = null;
        for (Target target : source.getTargets()) {
            if (target instanceof TargetPermanent) {
                targetCreature = game.getPermanent(target.getFirstTarget());
            }
        }
        if (targetCreature != null) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(targetCreature.getId()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
