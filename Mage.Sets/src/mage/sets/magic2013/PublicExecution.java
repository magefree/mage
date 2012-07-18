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
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class PublicExecution extends CardImpl<PublicExecution> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    
    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public PublicExecution(UUID ownerId) {
        super(ownerId, 105, "Public Execution", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{5}{B}");
        this.expansionSetCode = "M13";

        this.color.setBlack(true);

        // Destroy target creature an opponent controls. Each other creature that player controls gets -2/-0 until end of turn.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new PublicExecutionEffect());
    }

    public PublicExecution(final PublicExecution card) {
        super(card);
    }

    @Override
    public PublicExecution copy() {
        return new PublicExecution(this);
    }
}

class PublicExecutionEffect extends OneShotEffect<PublicExecutionEffect> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature an opponent controls");
    
    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }
    
    public PublicExecutionEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "Each other creature that player controls gets -2/-0 until end of turn";
    }

    public PublicExecutionEffect(final PublicExecutionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Constants.Zone.BATTLEFIELD);
        if (target != null) {
            UUID opponent = target.getControllerId();
            if (opponent != null) {
                for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter, game)) {
                    if (creature != null && creature.getControllerId() == opponent && creature != target) {
                        creature.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(-2, 0, Constants.Duration.EndOfTurn)), game);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public PublicExecutionEffect copy() {
        return new PublicExecutionEffect(this);
    }
}