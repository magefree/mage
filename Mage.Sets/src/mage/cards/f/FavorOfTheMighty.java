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
package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class FavorOfTheMighty extends CardImpl {

    public FavorOfTheMighty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add("Giant");


        // Each creature with the highest converted mana cost has protection from all colors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FavorOfTheMightyEffect()));
    }

    public FavorOfTheMighty(final FavorOfTheMighty card) {
        super(card);
    }

    @Override
    public FavorOfTheMighty copy() {
        return new FavorOfTheMighty(this);
    }
}

@SuppressWarnings("unchecked")
class FavorOfTheMightyEffect extends ContinuousEffectImpl {
    
    private static final FilterCard filter = new FilterCard("all colors");
    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.GREEN)));
    }
    
    FavorOfTheMightyEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each creature with the highest converted mana cost has protection from all colors.";
    }
    
    FavorOfTheMightyEffect(final FavorOfTheMightyEffect effect) {
        super(effect);
    }
    
    @Override
    public FavorOfTheMightyEffect copy() {
        return new FavorOfTheMightyEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        int maxCMC = Integer.MIN_VALUE;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
            if (permanent != null && permanent.getConvertedManaCost() > maxCMC) {
                maxCMC = permanent.getConvertedManaCost();
            }
        }
        FilterPermanent filterMaxCMC = new FilterCreaturePermanent();
        filterMaxCMC.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, maxCMC));
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterMaxCMC, source.getControllerId(), game)) {
            if (permanent != null) {
                permanent.addAbility(new ProtectionAbility(filter), source.getSourceId(), game);
            }
        }
        return true;
    }
}