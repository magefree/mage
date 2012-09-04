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
package mage.sets.innistrad;

import java.util.Iterator;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class ManorGargoyle extends CardImpl<ManorGargoyle> {

    private static final String rule = "{this} is indestructible as long as it has defender.";

    public ManorGargoyle(UUID ownerId) {
        super(ownerId, 228, "Manor Gargoyle", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Gargoyle");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(DefenderAbility.getInstance());
        // Manor Gargoyle is indestructible as long as it has defender.
        ConditionalContinousEffect effect = new ConditionalContinousEffect(new GainAbilitySourceEffect(new IndestructibleAbility()), HasDefenderCondition.getInstance(), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // {1}: Until end of turn, Manor Gargoyle loses defender and gains flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GargoyleSentinelEffect(), new ManaCostsImpl("{1}")));
    }

    public ManorGargoyle(final ManorGargoyle card) {
        super(card);
    }

    @Override
    public ManorGargoyle copy() {
        return new ManorGargoyle(this);
    }
}

class GargoyleSentinelEffect extends ContinuousEffectImpl<GargoyleSentinelEffect> {

    public GargoyleSentinelEffect() {
        super(Duration.EndOfTurn, Outcome.AddAbility);
        staticText = "Until end of turn, {this} loses defender and gains flying";
    }

    public GargoyleSentinelEffect(final GargoyleSentinelEffect effect) {
        super(effect);
    }

    @Override
    public GargoyleSentinelEffect copy() {
        return new GargoyleSentinelEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        for (Iterator<Ability> ability = permanent.getAbilities().iterator(); ability.hasNext();) {
                            Ability entry = ability.next();
                            if (entry.getId().equals(DefenderAbility.getInstance().getId())) {
                                ability.remove();
                            }
                        }
                        permanent.getAbilities().add(FlyingAbility.getInstance());
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6;
    }
}

class HasDefenderCondition implements Condition {

    private static HasDefenderCondition INSTANCE;

    private HasDefenderCondition() {
    }

    public static HasDefenderCondition getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HasDefenderCondition();
        }
        return INSTANCE;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Abilities<Ability> abilities = permanent.getAbilities();
            for (Ability ability : abilities) {
                if (ability.getClass().equals(DefenderAbility.getInstance().getClass())) {
                    return true;
                }
            }
        }
        return false;
    }
}
