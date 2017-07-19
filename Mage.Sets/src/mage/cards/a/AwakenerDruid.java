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

package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AwakenerDruid extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent(SubType.FOREST, "Forest");

    public AwakenerDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Awakener Druid enters the battlefield, target Forest becomes a 4/5 green Treefolk creature for as long as Awakener Druid remains on the battlefield. It's still a land.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AwakenerDruidBecomesCreatureEffect(), false);
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);
    }


    public AwakenerDruid(final AwakenerDruid card) {
        super(card);
    }

    @Override
    public AwakenerDruid copy() {
        return new AwakenerDruid(this);
    }
}

class AwakenerDruidBecomesCreatureEffect extends BecomesCreatureTargetEffect {

    public AwakenerDruidBecomesCreatureEffect() {
        super(new AwakenerDruidToken(), false, true, Duration.WhileOnBattlefield);
    }

    public AwakenerDruidBecomesCreatureEffect(final AwakenerDruidBecomesCreatureEffect effect) {
        super(effect);
    }

    @Override
    public AwakenerDruidBecomesCreatureEffect copy() {
        return new AwakenerDruidBecomesCreatureEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            this.discard();
            return false;
        }
        return super.apply(layer, sublayer, source, game);
    }

    @Override
    public String getText(Mode mode) {
        return "target Forest becomes a 4/5 green Treefolk creature for as long as {this} remains on the battlefield. It's still a land";
    }
}

class AwakenerDruidToken extends Token {

    public AwakenerDruidToken() {
        super("", "4/5 green Treefolk creature as long as {this} is on the battlefield");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TREEFOLK);
        color.setGreen(true);
        power = new MageInt(4);
        toughness = new MageInt(5);
    }

}
