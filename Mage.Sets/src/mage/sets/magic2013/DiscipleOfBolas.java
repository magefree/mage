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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class DiscipleOfBolas extends CardImpl<DiscipleOfBolas> {
    
    final private static FilterCreaturePermanent filter = new FilterCreaturePermanent(" another creature");

    static {
        filter.setAnother(true);
        filter.setTargetController(Constants.TargetController.YOU);
    }

    public DiscipleOfBolas(UUID ownerId) {
        super(ownerId, 88, "Disciple of Bolas", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "M13";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Disciple of Bolas enters the battlefield, sacrifice another creature. You gain X life and draw X cards, where X is that creature's power.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscipleOfBolasEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public DiscipleOfBolas(final DiscipleOfBolas card) {
        super(card);
    }

    @Override
    public DiscipleOfBolas copy() {
        return new DiscipleOfBolas(this);
    }
}

class DiscipleOfBolasEffect extends OneShotEffect<DiscipleOfBolasEffect> {

    public DiscipleOfBolasEffect() {
        super(Constants.Outcome.Benefit);
        this.staticText = "sacrifice another creature. You gain X life and draw X cards, where X is that creature's power";
    }

    public DiscipleOfBolasEffect(final DiscipleOfBolasEffect effect) {
        super(effect);
    }

    @Override
    public DiscipleOfBolasEffect copy() {
        return new DiscipleOfBolasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sacrificed = game.getPermanent(source.getFirstTarget());
        System.out.println("The target is " + sacrificed.getName());
        Player player = game.getPlayer(source.getControllerId());
        if (sacrificed != null && player != null) {
            sacrificed.sacrifice(source.getSourceId(), game);
            Permanent lastKnownState = (Permanent) game.getLastKnownInformation(sacrificed.getId(), Constants.Zone.BATTLEFIELD);
            int power = lastKnownState.getPower().getValue();
            player.gainLife(power, game);
            player.drawCards(power, game); 
            return true;
        }
        return false;
    }
}


