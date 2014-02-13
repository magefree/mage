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
package mage.sets.iceage;

import java.util.List;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SkipUntapAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class WrathOfMaritLage extends CardImpl<WrathOfMaritLage> {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent("red creatures");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }
    
    
    public WrathOfMaritLage(UUID ownerId) {
        super(ownerId, 109, "Wrath of Marit Lage", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");
        this.expansionSetCode = "ICE";

        this.color.setBlue(true);

        // When Wrath of Marit Lage enters the battlefield, tap all red creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapAllEffect()));
        // Red creatures don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipUntapAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    public WrathOfMaritLage(final WrathOfMaritLage card) {
        super(card);
    }

    @Override
    public WrathOfMaritLage copy() {
        return new WrathOfMaritLage(this);
    }
}

class TapAllEffect extends OneShotEffect<TapAllEffect> {
   
    public TapAllEffect() {
        super(Outcome.Tap);
        staticText = "tap all red creatures";
    }

    public TapAllEffect(final TapAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        List<Permanent> creatures = game.getBattlefield().getActivePermanents(WrathOfMaritLage.filter, source.getSourceId(), game);
        for (Permanent creature : creatures) {
            creature.tap(game);
        }
        return true;
    }

    @Override
    public TapAllEffect copy() {
        return new TapAllEffect(this);
    }
}
