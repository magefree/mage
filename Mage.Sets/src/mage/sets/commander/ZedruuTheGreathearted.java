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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsYouOwnThatOpponentsControlCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author andyfries
 */
public class ZedruuTheGreathearted extends CardImpl {

    public ZedruuTheGreathearted(UUID ownerId) {
        super(ownerId, 240, "Zedruu the Greathearted", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{U}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Minotaur");
        this.subtype.add("Monk");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you gain X life and draw X cards, where X is the number of permanents you own that your opponents control.
        Effect effect = new GainLifeEffect(new PermanentsYouOwnThatOpponentsControlCount());
        effect.setText("you gain X life");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false);
        effect = new DrawCardSourceControllerEffect(new PermanentsYouOwnThatOpponentsControlCount());
        effect.setText("and draw X cards, where X is the number of permanents you own that your opponents control");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {R}{W}{U}: Target opponent gains control of target permanent you control.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZedruuTheGreatheartedEffect(), new ManaCostsImpl("{R}{W}{U}"));
        ability.addTarget(new TargetOpponent());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    public ZedruuTheGreathearted(final ZedruuTheGreathearted card) {
        super(card);
    }

    @Override
    public ZedruuTheGreathearted copy() {
        return new ZedruuTheGreathearted(this);
    }

    class ZedruuTheGreatheartedEffect extends ContinuousEffectImpl {

        private MageObjectReference targetPermanentReference;

        public ZedruuTheGreatheartedEffect() {
            super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
            this.staticText = "Target opponent gains control of target permanent you control";
        }

        public ZedruuTheGreatheartedEffect(final ZedruuTheGreatheartedEffect effect) {
            super(effect);
            this.targetPermanentReference = effect.targetPermanentReference;
        }

        @Override
        public ZedruuTheGreatheartedEffect copy() {
            return new ZedruuTheGreatheartedEffect(this);
        }

        @Override
        public void init(Ability source, Game game) {
            super.init(source, game);
            targetPermanentReference = new MageObjectReference(source.getTargets().get(1).getFirstTarget(), game);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = targetPermanentReference.getPermanent(game);
            if (permanent != null) {
                return permanent.changeControllerId(source.getFirstTarget(), game);
            } else {
                discard();
            }
            return false;
        }
    }
}
