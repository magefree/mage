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
package mage.cards.o;

import java.util.UUID;

import mage.constants.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class OgreJailbreaker extends CardImpl {

    public OgreJailbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add("Ogre");
        this.subtype.add("Rogue");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Ogre Jailbreaker can attack as though it didn't have defender as long as you control a Gate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OgreJailbreakerEffect()));

    }

    public OgreJailbreaker(final OgreJailbreaker card) {
        super(card);
    }

    @Override
    public OgreJailbreaker copy() {
        return new OgreJailbreaker(this);
    }
}

class OgreJailbreakerEffect extends AsThoughEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent();
    private PermanentsOnTheBattlefieldCondition gateCondition;
    static {
        filter.add(new SubtypePredicate(SubType.GATE));
    }

    public OgreJailbreakerEffect() {
        super(AsThoughEffectType.ATTACK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "{this} can attack as though it didn't have defender as long as you control a Gate";
        gateCondition = new PermanentsOnTheBattlefieldCondition(filter);
    }

    public OgreJailbreakerEffect(final OgreJailbreakerEffect effect) {
        super(effect);
        this.gateCondition = effect.gateCondition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OgreJailbreakerEffect copy() {
        return new OgreJailbreakerEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId()) && gateCondition.apply(game, source))  {
            return true;
        }
        return false;
    }

}