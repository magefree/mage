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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */
public class WortTheRaidmother extends CardImpl {

    public WortTheRaidmother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R/G}{R/G}");
        this.supertype.add("Legendary");
        this.subtype.add("Goblin");
        this.subtype.add("Shaman");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Wort, the Raidmother enters the battlefield, create two 1/1 red and green Goblin Warrior creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WortTheRaidmotherToken(), 2), false));

        // Each red or green instant or sorcery spell you cast has conspire.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WortGainConspireEffect()));
    }

    public WortTheRaidmother(final WortTheRaidmother card) {
        super(card);
    }

    @Override
    public WortTheRaidmother copy() {
        return new WortTheRaidmother(this);
    }
}

class WortGainConspireEffect extends ContinuousEffectImpl {

    private final static FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell();

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.GREEN)));
    }
    private final ConspireAbility conspireAbility;

    public WortGainConspireEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each red or green instant or sorcery spell you cast has conspire. <i>(As you cast the spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.)</i>";
        conspireAbility = new ConspireAbility(getId(), ConspireAbility.ConspireTargets.MORE);
    }

    public WortGainConspireEffect(final WortGainConspireEffect effect) {
        super(effect);
        this.conspireAbility = new ConspireAbility(getId(), ConspireAbility.ConspireTargets.MORE);
    }

    @Override
    public WortGainConspireEffect copy() {
        return new WortGainConspireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (StackObject stackObject : game.getStack()) {
            // only spells cast, so no copies of spells
            if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.getControllerId().equals(source.getControllerId())) {
                Spell spell = (Spell) stackObject;
                if (filter.match(stackObject, game)) {
                    game.getState().addOtherAbility(spell.getCard(), conspireAbility);
                }
            }
        }
        return true;
    }
}

class WortTheRaidmotherToken extends Token {

    public WortTheRaidmotherToken() {
        super("Goblin Warrior", "1/1 red and green Goblin Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add("Goblin");
        subtype.add("Warrior");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
