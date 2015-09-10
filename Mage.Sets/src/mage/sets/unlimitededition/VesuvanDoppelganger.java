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
package mage.sets.unlimitededition;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class VesuvanDoppelganger extends CardImpl {

    private static final String rule = "You may have {this} enter the battlefield as a copy of any creature on the battlefield except it doesn't copy that creature's color and it gains \"At the beginning of your upkeep, you may have this creature become a copy of target creature except it doesn't copy that creature's color. If you do, this creature gains this ability.\"";

    public VesuvanDoppelganger(UUID ownerId) {
        super(ownerId, 88, "Vesuvan Doppelganger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "2ED";
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Vesuvan Doppelganger enter the battlefield as a copy of any creature on the battlefield except it doesn't copy that creature's color and it gains "At the beginning of your upkeep, you may have this creature become a copy of target creature except it doesn't copy that creature's color. If you do, this creature gains this ability."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new EntersBattlefieldEffect(new VesuvanDoppelgangerCopyEffect(), rule, true));
        this.addAbility(ability);

    }

    public VesuvanDoppelganger(final VesuvanDoppelganger card) {
        super(card);
    }

    @Override
    public VesuvanDoppelganger copy() {
        return new VesuvanDoppelganger(this);
    }
}

class VesuvanDoppelgangerCopyEffect extends OneShotEffect {

    private static final String rule2 = "At the beginning of your upkeep, you may have this creature become a copy of target creature except it doesn't copy that creature's color. If you do, this creature gains this ability.";

    public VesuvanDoppelgangerCopyEffect() {
        super(Outcome.Copy);
    }

    public VesuvanDoppelgangerCopyEffect(final VesuvanDoppelgangerCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        final Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Target target = new TargetPermanent(new FilterCreaturePermanent("target creature (you copy from)"));
            target.setRequired(true);
            if (source instanceof SimpleStaticAbility) {
                target = new TargetPermanent(new FilterCreaturePermanent("creature (you copy from)"));
                target.setRequired(false);
                target.setNotTarget(true);
            }
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                controller.choose(Outcome.Copy, target, source.getSourceId(), game);
                Permanent copyFromPermanent = game.getPermanent(target.getFirstTarget());
                if (copyFromPermanent != null) {
                    game.copyPermanent(copyFromPermanent, sourcePermanent, source, new ApplyToPermanent() {
                        @Override
                        public Boolean apply(Game game, Permanent permanent) {
                            permanent.getColor(game).setColor(sourcePermanent.getColor(game));
                            permanent.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                                    new VesuvanDoppelgangerCopyEffect(), TargetController.YOU, true, false, rule2));
                            return true;
                        }

                        @Override
                        public Boolean apply(Game game, MageObject mageObject) {
                            mageObject.getColor(game).setColor(sourcePermanent.getColor(game));
                            mageObject.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                                    new VesuvanDoppelgangerCopyEffect(), TargetController.YOU, true, false, rule2));
                            return true;
                        }

                    });
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public VesuvanDoppelgangerCopyEffect copy() {
        return new VesuvanDoppelgangerCopyEffect(this);
    }
}
