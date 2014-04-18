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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ScryEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class StormchaserChimera extends CardImpl<StormchaserChimera> {

    public StormchaserChimera(UUID ownerId) {
        super(ownerId, 156, "Stormchaser Chimera", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Chimera");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {2}{U}{R}: Scry 1, then reveal the top card of your library. Stormchaser Chimera gets +X/+0 until end of turn, where X is that card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1), new ManaCostsImpl("{2}{U}{R}"));
        ability.addEffect(new StormchaserChimeraEffect());
        this.addAbility(ability);
    }

    public StormchaserChimera(final StormchaserChimera card) {
        super(card);
    }

    @Override
    public StormchaserChimera copy() {
        return new StormchaserChimera(this);
    }
}

class StormchaserChimeraEffect extends OneShotEffect<StormchaserChimeraEffect> {

    public StormchaserChimeraEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then reveal the top card of your library. Stormchaser Chimera gets +X/+0 until end of turn, where X is that card's converted mana cost";
    }

    public StormchaserChimeraEffect(final StormchaserChimeraEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield((source.getSourceId()));
        if (player == null || sourcePermanent == null) {
            return false;
        }
        if (player.getLibrary().size() > 0) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            player.revealCards(sourcePermanent.getName(), cards, game);

            if (card != null) {
                return new BoostSourceEffect(card.getManaCost().convertedManaCost(), 0, Duration.EndOfTurn).apply(game, source);
            }
        }
        return false;
    }

    @Override
    public StormchaserChimeraEffect copy() {
        return new StormchaserChimeraEffect(this);
    }
}
