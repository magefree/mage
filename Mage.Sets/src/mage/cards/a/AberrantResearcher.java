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
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.p.PerfectedForm;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class AberrantResearcher extends CardImpl {

    public AberrantResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add("Human");
        this.subtype.add("Insect");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.transformable = true;
        this.secondSideCardClazz = PerfectedForm.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, put the top card of your library into your graveyard. If it's an instant or sorcery card, transform Aberrant Researcher.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AberrantResearcherEffect(), TargetController.YOU, false));
    }

    public AberrantResearcher(final AberrantResearcher card) {
        super(card);
    }

    @Override
    public AberrantResearcher copy() {
        return new AberrantResearcher(this);
    }
}

class AberrantResearcherEffect extends OneShotEffect {

    public AberrantResearcherEffect() {
        super(Outcome.Benefit);
        staticText = "put the top card of your library into your graveyard. If it's an instant or sorcery card, transform {this}";
    }

    public AberrantResearcherEffect(final AberrantResearcherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            controller.moveCards(card, Zone.GRAVEYARD, source, game);
            if (card.isInstant() || card.isSorcery()) {
                new TransformSourceEffect(true).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public AberrantResearcherEffect copy() {
        return new AberrantResearcherEffect(this);
    }
}
