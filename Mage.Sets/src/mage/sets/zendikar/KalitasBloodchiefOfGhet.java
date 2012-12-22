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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class KalitasBloodchiefOfGhet extends CardImpl<KalitasBloodchiefOfGhet> {

    public KalitasBloodchiefOfGhet(UUID ownerId) {
        super(ownerId, 99, "Kalitas, Bloodchief of Ghet", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Vampire");
        this.subtype.add("Warrior");

        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {B}{B}{B}, {T}: Destroy target creature. If that creature dies this way, put a black Vampire creature token onto the battlefield. Its power is equal to that creature's power and its toughness is equal to that creature's toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KalitasDestroyEffect(), new ManaCostsImpl("{B}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public KalitasBloodchiefOfGhet(final KalitasBloodchiefOfGhet card) {
        super(card);
    }

    @Override
    public KalitasBloodchiefOfGhet copy() {
        return new KalitasBloodchiefOfGhet(this);
    }
}

class KalitasDestroyEffect extends OneShotEffect<KalitasDestroyEffect> {

    public KalitasDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. If that creature dies this way, put a black Vampire creature token onto the battlefield. Its power is equal to that creature's power and its toughness is equal to that creature's toughness";
    }

    public KalitasDestroyEffect(final KalitasDestroyEffect effect) {
        super(effect);
    }

    @Override
    public KalitasDestroyEffect copy() {
        return new KalitasDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                Card card = game.getCard(permanent.getId());
                int zoneChangeCounter = card.getZoneChangeCounter();
                if (permanent.destroy(source.getSourceId(), game, false)) {
                    if (card != null && game.getPlayer(permanent.getOwnerId()).getGraveyard().contains(card.getId()) &&
                        card.getZoneChangeCounter() == zoneChangeCounter)
                    {
                        return new CreateTokenEffect(new VampireToken(permanent.getPower().getValue(), permanent.getToughness().getValue())).apply(game, source);
                    }
                }
            }
        }
        return false;
    }
}

class VampireToken extends Token {

    public VampireToken(int tokenPower, int tokenToughness) {
        super("Vampire", new StringBuilder(tokenPower).append("/").append(tokenToughness).append(" black Vampire creature token").toString());
        cardType.add(CardType.CREATURE);
        color = ObjectColor.BLACK;
        subtype.add("Vampire");
        power = new MageInt(tokenPower);
        toughness = new MageInt(tokenToughness);
    }

}