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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DesertersQuarters extends CardImpl<DesertersQuarters> {

    public DesertersQuarters(UUID ownerId) {
        super(ownerId, 160, "Deserter's Quarters", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "JOU";

        // You may choose not to untap Deserter's Quarters during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {6}, T: Tap target creature. It doesn't untap during its controller's untap step for as long as Deserter's Quarters remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DesertersQuartersTapTargetEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DesertersQuartersRestrictionEffect()));
    }

    public DesertersQuarters(final DesertersQuarters card) {
        super(card);
    }

    @Override
    public DesertersQuarters copy() {
        return new DesertersQuarters(this);
    }
}

class DesertersQuartersTapTargetEffect extends TapTargetEffect {

    public DesertersQuartersTapTargetEffect() {
        super();
        staticText = "Tap target creature. It doesn't untap during its controller's untap step for as long as {this} remains tapped";
    }

    public DesertersQuartersTapTargetEffect(final DesertersQuartersTapTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            sourcePermanent.clearConnectedCards("DesertersQuarters");
        }
        for (UUID target : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(target);
            if (sourcePermanent != null) {
                sourcePermanent.addConnectedCard("DesertersQuarters", permanent.getId());
            }
            if (permanent != null) {             
                permanent.tap(game);
            }
        }
        return true;
    }


    @Override
    public DesertersQuartersTapTargetEffect copy() {
        return new DesertersQuartersTapTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return staticText;
    }
}

class DesertersQuartersRestrictionEffect extends RestrictionEffect<DesertersQuartersRestrictionEffect> {

    public DesertersQuartersRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
    }

    public DesertersQuartersRestrictionEffect(final DesertersQuartersRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent rustTick = game.getPermanent(source.getSourceId());
        if (rustTick != null && rustTick.isTapped()) {
            if (rustTick.getConnectedCards("DesertersQuarters").size() > 0) {
                UUID target = rustTick.getConnectedCards("DesertersQuarters").get(0);
                if (target != null && target.equals(permanent.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canBeUntapped(Permanent permanent, Game game) {
        return false;
    }

    @Override
    public DesertersQuartersRestrictionEffect copy() {
        return new DesertersQuartersRestrictionEffect(this);
    }

}
