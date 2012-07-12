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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class ThundermawHellkite extends CardImpl<ThundermawHellkite> {
    
    final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying your opponents control");
    
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.setTargetController(Constants.TargetController.OPPONENT);
    }

    public ThundermawHellkite(UUID ownerId) {
        super(ownerId, 150, "Thundermaw Hellkite", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "M13";
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // When Thundermaw Hellkite enters the battlefield, it deals 1 damage to each creature with flying your opponents control. Tap those creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageAllEffect(1, filter));
        ability.addEffect(new TapAllEffect(filter));
        this.addAbility(ability);
    }

    public ThundermawHellkite(final ThundermawHellkite card) {
        super(card);
    }

    @Override
    public ThundermawHellkite copy() {
        return new ThundermawHellkite(this);
    }
}

class TapAllEffect extends OneShotEffect<TapAllEffect> {
    
    private FilterCreaturePermanent filter;

    public TapAllEffect(FilterCreaturePermanent filter) {
        super(Constants.Outcome.Tap);
        this.filter = filter;
        staticText = "Tap those creatures";
    }

    public TapAllEffect(final TapAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public TapAllEffect copy() {
        return new TapAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.tap(game);
        }
        return true;
    }
}


