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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class SpellstutterSprite extends CardImpl<SpellstutterSprite> {
    
    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent("number of Faeries you control");
    static {
        filter.add(new ControllerPredicate(Constants.TargetController.YOU));
        filter.add(new SubtypePredicate("Faerie"));
    }

    public SpellstutterSprite(UUID ownerId) {
        super(ownerId, 89, "Spellstutter Sprite", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
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
            xFilter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, numberFaeries + 1));
            ability.getTargets().clear();
            ability.addTarget(new TargetSpell(xFilter));
        }
    }

    @Override
    public SpellstutterSprite copy() {
        return new SpellstutterSprite(this);
    }
}

class SpellstutterSpriteCounterTargetEffect extends OneShotEffect<SpellstutterSpriteCounterTargetEffect> {

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
        if (stackObject.getManaCost().convertedManaCost() <= numberFaeries) {
            if (game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
                game.informPlayers(new StringBuilder(stackObject.getName()).append(" was countered").toString());
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
