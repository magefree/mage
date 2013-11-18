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
package mage.sets.invasion;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class WashOut extends CardImpl<WashOut> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public WashOut(UUID ownerId) {
        super(ownerId, 87, "Wash Out", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "INV";

        this.color.setBlue(true);

        // Return all permanents of the color of your choice to their owners' hands.
        this.getSpellAbility().addChoice(new ChoiceColor());
        this.getSpellAbility().addEffect(new WashOutEffect());

    }

    public WashOut(final WashOut card) {
        super(card);
    }

    @Override
    public WashOut copy() {
        return new WashOut(this);
    }
}

class WashOutEffect extends OneShotEffect<WashOutEffect> {

    public WashOutEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return all permanents of the color of your choice to their owners' hands";
    }

    public WashOutEffect(final WashOutEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filter = new FilterPermanent();
        ObjectColor color = ((ChoiceColor) source.getChoices().get(0)).getColor();
        if (color != null) {
            filter.add(new ColorPredicate(color));

            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                permanent.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public WashOutEffect copy() {
        return new WashOutEffect(this);
    }
}
