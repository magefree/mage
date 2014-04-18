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
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class HourOfNeed extends CardImpl<HourOfNeed> {

    public HourOfNeed(UUID ownerId) {
        super(ownerId, 40, "Hour of Need", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "JOU";

        this.color.setBlue(true);

        // Strive — Hour of Need costs {1}{U} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{1}{U}"));
        // Exile any number of target creatures. For each creature exiled this way, its controller puts a 4/4 blue Sphinx creature token with flying onto the battlefield.
        this.getSpellAbility().addEffect(new HourOfNeedExileEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
    }

    public HourOfNeed(final HourOfNeed card) {
        super(card);
    }

    @Override
    public HourOfNeed copy() {
        return new HourOfNeed(this);
    }
}

class HourOfNeedExileEffect extends OneShotEffect<HourOfNeedExileEffect> {

    public HourOfNeedExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile any number of target creatures. For each creature exiled this way, its controller puts a 4/4 blue Sphinx creature token with flying onto the battlefield";
    }

    public HourOfNeedExileEffect(final HourOfNeedExileEffect effect) {
        super(effect);
    }

    @Override
    public HourOfNeedExileEffect copy() {
        return new HourOfNeedExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for(UUID creatureId: getTargetPointer().getTargets(game, source)) {
                Permanent creature = game.getPermanent(creatureId);
                if (creature != null) {
                    controller.moveCardToExileWithInfo(creature, null, null, source.getSourceId(), game, Zone.BATTLEFIELD);
                    Token token = new HourOfNeedSphinxToken();
                    token.putOntoBattlefield(1, game, source.getSourceId(), creature.getControllerId());
                }
            }
            return true;
        }
        return false;
    }
}

class HourOfNeedSphinxToken extends Token {

    public HourOfNeedSphinxToken() {
        super("Sphinx", "4/4 blue Sphinx creature token with flying");
        this.setOriginalExpansionSetCode("JOU");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.BLUE;
        subtype.add("Sphinx");
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }
}