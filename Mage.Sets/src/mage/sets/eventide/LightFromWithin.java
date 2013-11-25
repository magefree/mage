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
package mage.sets.eventide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public class LightFromWithin extends CardImpl<LightFromWithin> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public LightFromWithin(UUID ownerId) {
        super(ownerId, 10, "Light from Within", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "EVE";

        this.color.setWhite(true);

        // Chroma - Each creature you control gets +1/+1 for each white mana symbol in its mana cost.
        Effect effect = new LightFromWithinEffect();
        effect.setText("<i>Chroma</i> - Each creature you control gets +1/+1 for each white mana symbol in its mana cost.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    public LightFromWithin(final LightFromWithin card) {
        super(card);
    }

    @Override
    public LightFromWithin copy() {
        return new LightFromWithin(this);
    }
}

class LightFromWithinEffect extends ContinuousEffectImpl<LightFromWithinEffect> {

    boolean boosted = false;

    public LightFromWithinEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
    }

    public LightFromWithinEffect(final LightFromWithinEffect effect) {
        super(effect);
    }

    @Override
    public LightFromWithinEffect copy() {
        return new LightFromWithinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), game)) {
            if (creature != null) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    creature.addPower(new ChromaLightFromWithinCount(creature).calculate(game, source));
                    creature.addToughness(new ChromaLightFromWithinCount(creature).calculate(game, source));
                    boosted = true;
                }
            }
        }
        return boosted;
    }
}

class ChromaLightFromWithinCount implements DynamicValue {

    private Permanent permanent;

    public ChromaLightFromWithinCount(Permanent permanent) {
        this.permanent = permanent;
    }

    public ChromaLightFromWithinCount(final ChromaLightFromWithinCount dynamicValue) {
        this.permanent = dynamicValue.permanent;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int chroma = 0;
        chroma += permanent.getManaCost().getMana().getWhite();
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaLightFromWithinCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
