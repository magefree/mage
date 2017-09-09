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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class EldraziMimic extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("another colorless creature");

    static {
        FILTER.add(new AnotherPredicate());
        FILTER.add(new ColorlessPredicate());
    }

    public EldraziMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever another colorless creature enters the battlefield under your control, you may have the base power and toughness of Eldrazi Mimic
        // become that creature's power and toughness until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new EldraziMimicEffect(), FILTER, true, SetTargetPointer.PERMANENT, null));
    }

    public EldraziMimic(final EldraziMimic card) {
        super(card);
    }

    @Override
    public EldraziMimic copy() {
        return new EldraziMimic(this);
    }
}

class EldraziMimicEffect extends OneShotEffect {

    public EldraziMimicEffect() {
        super(Outcome.Detriment);
        staticText = "you may have the base power and toughness of {this} become that creature's power and toughness until end of turn";
    }

    public EldraziMimicEffect(final EldraziMimicEffect effect) {
        super(effect);
    }

    @Override
    public EldraziMimicEffect copy() {
        return new EldraziMimicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = ((FixedTarget) getTargetPointer()).getTargetedPermanentOrLKIBattlefield(game);
            if (permanent != null) {
                ContinuousEffect effect = new SetPowerToughnessTargetEffect(permanent.getPower().getValue(), permanent.getToughness().getValue(), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(source.getSourceId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}
