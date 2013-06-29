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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class KnightOfNewAlara extends CardImpl<KnightOfNewAlara> {

    public KnightOfNewAlara(UUID ownerId) {
        super(ownerId, 70, "Knight of New Alara", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Human");
        this.subtype.add("Knight");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each other multicolored creature you control gets +1/+1 for each of its colors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KnightOfNewAlaraEffect()));

    }

    public KnightOfNewAlara(final KnightOfNewAlara card) {
        super(card);
    }

    @Override
    public KnightOfNewAlara copy() {
        return new KnightOfNewAlara(this);
    }
}

class KnightOfNewAlaraEffect extends ContinuousEffectImpl<KnightOfNewAlaraEffect> {

    public KnightOfNewAlaraEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.BoostCreature);
        staticText = "Each other multicolored creature you control gets +1/+1 for each of its colors";
    }

    public KnightOfNewAlaraEffect(final KnightOfNewAlaraEffect effect) {
        super(effect);
    }

    @Override
    public KnightOfNewAlaraEffect copy() {
        return new KnightOfNewAlaraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        filter.add(new MulticoloredPredicate());
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            DynamicValue colors = new KnightOfNewAlaraColorCount(creature);
            if (creature != null
                    && creature != game.getPermanent(source.getSourceId())) {
                creature.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(colors, colors, Duration.WhileOnBattlefield)), source.getId(), game);
            }
        }
        return true;
    }
}

class KnightOfNewAlaraColorCount implements DynamicValue {

    final Permanent creature;
    private int count;

    public KnightOfNewAlaraColorCount(Permanent creature) {
        this.creature = creature;
        this.count = 0;
    }

    public KnightOfNewAlaraColorCount(final KnightOfNewAlaraColorCount dynamicValue) {
        this.creature = dynamicValue.creature;
        this.count = dynamicValue.count;
    }

    @Override
    public int calculate(Game game, Ability source) {
        if (creature != null) {
            count = creature.getColor().getColorCount();
        }
        return count;
    }

    @Override
    public DynamicValue copy() {
        return new KnightOfNewAlaraColorCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "of its colors";
    }
}