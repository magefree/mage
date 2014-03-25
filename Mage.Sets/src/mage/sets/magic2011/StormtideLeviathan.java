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

package mage.sets.magic2011;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.combat.CantAttackAllAnyPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class StormtideLeviathan extends CardImpl<StormtideLeviathan> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures without flying or islandwalk");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter.add(Predicates.not(new AbilityPredicate(IslandwalkAbility.class)));
    }

    public StormtideLeviathan(UUID ownerId) {
        super(ownerId, 74, "Stormtide Leviathan", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{U}{U}{U}");
        this.expansionSetCode = "M11";
        this.subtype.add("Leviathan");
        this.color.setBlue(true);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Islandwalk (This creature is unblockable as long as defending player controls an Island.)
        this.addAbility(new IslandwalkAbility());
        // All lands are Islands in addition to their other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StormtideLeviathanEffect()));
        // Creatures without flying or islandwalk can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAllAnyPlayerEffect(Duration.WhileOnBattlefield, filter)));

    }

    public StormtideLeviathan(final StormtideLeviathan card) {
        super(card);
    }

    @Override
    public StormtideLeviathan copy() {
        return new StormtideLeviathan(this);
    }

}

class StormtideLeviathanEffect extends ContinuousEffectImpl<StormtideLeviathanEffect> {

    public StormtideLeviathanEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "All lands are Islands in addition to their other types";
    }

    public StormtideLeviathanEffect(final StormtideLeviathanEffect effect) {
        super(effect);
    }

    @Override
    public StormtideLeviathanEffect copy() {
        return new StormtideLeviathanEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent land : game.getBattlefield().getActivePermanents(new FilterLandPermanent(), source.getControllerId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (!land.getSubtype().contains("Island")) {
                        land.getSubtype().add("Island");
                    }
                    break;
                case AbilityAddingRemovingEffects_6:
                    boolean addAbility = true;
                    for (Ability existingAbility : land.getAbilities()) {
                        if (existingAbility instanceof BlueManaAbility) {
                            addAbility = false;
                            break;
                        }
                    }
                    if (addAbility) {
                        land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
