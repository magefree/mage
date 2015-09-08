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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.functions.AbilityApplier;

/**
 *
 * @author LevelX2
 */
public class MercurialPretender extends CardImpl {

    private static final String abilityText = "You may have {this} enter the battlefield as a copy of any creature you control except it gains \"{2}{U}{U}: Return this creature to its owner's hand.\"";

    public MercurialPretender(UUID ownerId) {
        super(ownerId, 68, "Mercurial Pretender", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "M15";
        this.subtype.add("Shapeshifter");

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Mercurial Pretender enter the battlefield as a copy of any creature you control
        // except it gains "{2}{U}{U}: Return this creature to its owner's hand."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new EntersBattlefieldEffect(
                new MercurialPretenderCopyEffect(), abilityText, true));
        this.addAbility(ability);
    }

    public MercurialPretender(final MercurialPretender card) {
        super(card);
    }

    @Override
    public MercurialPretender copy() {
        return new MercurialPretender(this);
    }
}

class MercurialPretenderCopyEffect extends OneShotEffect {

    public MercurialPretenderCopyEffect() {
        super(Outcome.Copy);
    }

    public MercurialPretenderCopyEffect(final MercurialPretenderCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            Target target = new TargetPermanent(new FilterControlledCreaturePermanent());
            target.setNotTarget(true);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                player.choose(Outcome.Copy, target, source.getSourceId(), game);
                Permanent copyFromPermanent = game.getPermanent(target.getFirstTarget());
                if (copyFromPermanent != null) {
                    game.copyPermanent(copyFromPermanent, sourcePermanent, source,
                            // {2}{U}{U}: Return this creature to its owner's hand.
                            new AbilityApplier(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl("{2}{U}{U}")))
                    );
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public MercurialPretenderCopyEffect copy() {
        return new MercurialPretenderCopyEffect(this);
    }
}
