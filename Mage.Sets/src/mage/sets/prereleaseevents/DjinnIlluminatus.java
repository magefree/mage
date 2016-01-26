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
package mage.sets.prereleaseevents;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */
public class DjinnIlluminatus extends CardImpl {

    public DjinnIlluminatus(UUID ownerId) {
        super(ownerId, 28, "Djinn Illuminatus", Rarity.SPECIAL, new CardType[]{CardType.CREATURE}, "{5}{U/R}{U/R}");
        this.expansionSetCode = "PTC";
        this.subtype.add("Djinn");
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // <i>({U/R} can be paid with either {U} or {R}.)</i>
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Each instant and sorcery spell you cast has replicate. The replicate cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DjinnIlluminatusGainReplicateEffect()));

    }

    public DjinnIlluminatus(final DjinnIlluminatus card) {
        super(card);
    }

    @Override
    public DjinnIlluminatus copy() {
        return new DjinnIlluminatus(this);
    }
}

class DjinnIlluminatusGainReplicateEffect extends ContinuousEffectImpl {

    private final static FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell();
    private final Map<UUID, ReplicateAbility> replicateAbilities = new HashMap<>();

    public DjinnIlluminatusGainReplicateEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each instant and sorcery spell you cast has replicate. The replicate cost is equal to its mana cost "
                + "<i>(When you cast it, copy it for each time you paid its replicate cost. You may choose new targets for the copies.)</i>";
    }

    public DjinnIlluminatusGainReplicateEffect(final DjinnIlluminatusGainReplicateEffect effect) {
        super(effect);
        this.replicateAbilities.putAll(replicateAbilities);
    }

    @Override
    public DjinnIlluminatusGainReplicateEffect copy() {
        return new DjinnIlluminatusGainReplicateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (StackObject stackObject : game.getStack()) {
            // only spells cast, so no copies of spells
            if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.getControllerId().equals(source.getControllerId())) {
                Spell spell = (Spell) stackObject;
                if (filter.match(stackObject, game)) {
                    ReplicateAbility replicateAbility = replicateAbilities.get(spell.getId());
                    if (replicateAbility == null) {
                        replicateAbility = new ReplicateAbility(spell.getCard(), spell.getSpellAbility().getManaCosts().getText());
                        replicateAbilities.put(spell.getId(), replicateAbility);
                    }
                    game.getState().addOtherAbility(spell.getCard(), replicateAbility, false); // Do not copy because paid and # of activations state is handled in the baility
                }
            }
        }
        if (game.getStack().isEmpty()) {
            replicateAbilities.clear();
        }
        return true;
    }
}
