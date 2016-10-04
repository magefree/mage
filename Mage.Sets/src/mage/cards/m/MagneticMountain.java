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
package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.UntapEnchantedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;



/**
 *
 * @author MarcoMarin
 */
public class MagneticMountain extends CardImpl {
    
    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }
    
    public MagneticMountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{R}");

        // Blue creatures don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
        // At the beginning of each player's upkeep, that player may choose any number of tapped blue creatures he or she controls and pay {4} for each creature chosen this way. If the player does, untap those creatures.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MagneticMountainEffect(), TargetController.ANY, false));
    }

    public MagneticMountain(final MagneticMountain card) {
        super(card);
    }

    @Override
    public MagneticMountain copy() {
        return new MagneticMountain(this);
    }
}
class MagneticMountainEffect extends DoIfCostPaid {

    public MagneticMountainEffect(){
    super(new UntapEnchantedEffect(), new GenericManaCost(4));
    }

    public MagneticMountainEffect(final MagneticMountainEffect effect) {
        super(effect);
    }

    @Override
    public MagneticMountainEffect copy() {
        return new MagneticMountainEffect(this);
    }

    @Override
    protected Player getPayingPlayer(Game game, Ability source) {
        return game.getPlayer(game.getActivePlayerId());
    }

    @Override
    public String getText(Mode mode) {
        return new StringBuilder("that player may ").append(getCostText())
                .append(". If he or she does, ").append(executingEffects.getText(mode)).toString();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures");
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(new MagneticMountainPredicate());
    
        if (game.getBattlefield().getAllActivePermanents(filter, game.getActivePlayerId(), game).size()>0){
            this.copy();                
        }
        return true;
        
    }
    class MagneticMountainPredicate implements ObjectPlayerPredicate<ObjectPlayer<Permanent>> {        
    @Override
    public boolean apply(ObjectPlayer<Permanent> input, Game game) {
        return input.getObject().isTapped();
                
    }
    @Override
    public String toString() {
        return "Tapped";
    }
}
}