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
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class SpellstutterSprite extends CardImpl {
    
    public static final FilterPermanent filter = new FilterPermanent("number of Faeries you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate("Faerie"));
    }

    public SpellstutterSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Spellstutter Sprite enters the battlefield, counter target spell with converted mana cost X or less, where X is the number of Faeries you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SpellstutterSpriteCounterTargetEffect()));
    }

    public SpellstutterSprite(final SpellstutterSprite card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            int numberFaeries = game.getState().getBattlefield().countAll(filter, ability.getControllerId(), game);
            FilterSpell xFilter = new FilterSpell(new StringBuilder("spell with converted mana cost ").append(numberFaeries).append(" or less").toString());
            xFilter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, numberFaeries + 1));
            ability.getTargets().clear();
            ability.addTarget(new TargetSpell(xFilter));
        }
    }

    @Override
    public SpellstutterSprite copy() {
        return new SpellstutterSprite(this);
    }
}

class SpellstutterSpriteCounterTargetEffect extends OneShotEffect {

    public SpellstutterSpriteCounterTargetEffect() {
        super(Outcome.Detriment);
    }

    public SpellstutterSpriteCounterTargetEffect(final SpellstutterSpriteCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public SpellstutterSpriteCounterTargetEffect copy() {
        return new SpellstutterSpriteCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        /*
         * The value of X needs to be determined both when the ability triggers (so you can choose
         * a target) and again when the ability resolves (to check if that target is still legal).
         * If the number of Faeries you control has decreased enough in that time to make the target
         * illegal, Spellstutter Sprite's ability will be countered (and the targeted spell will
         * resolve as normal).
         */
        int numberFaeries = game.getState().getBattlefield().countAll(SpellstutterSprite.filter, source.getControllerId(), game);
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        // If do'nt have any spell targeted
        if (stackObject != null && stackObject.getConvertedManaCost() <= numberFaeries) {
            if (game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "counter target spell with converted mana cost X or less, where X is the number of Faeries you control";
    }

}
