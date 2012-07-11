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

import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class MalakirBloodwitch extends CardImpl<MalakirBloodwitch> {

    private static final FilterCard filter = new FilterCard("white");

    static {
        filter.setUseColor(true);
        filter.getColor().setWhite(true);
    }

    public MalakirBloodwitch(UUID ownerId) {
        super(ownerId, 100, "Malakir Bloodwitch", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Vampire");
        this.subtype.add("Shaman");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new ProtectionAbility(filter));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MalakirBloodwitchEffect(), false));
    }

    public MalakirBloodwitch(final MalakirBloodwitch card) {
        super(card);
    }

    @Override
    public MalakirBloodwitch copy() {
        return new MalakirBloodwitch(this);
    }
}

class MalakirBloodwitchEffect extends OneShotEffect<MalakirBloodwitchEffect> {

    public MalakirBloodwitchEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent loses life equal to the number of Vampires you control. You gain life equal to the life lost this way";
    }

    public MalakirBloodwitchEffect(final MalakirBloodwitchEffect effect) {
        super(effect);
    }

    @Override
    public MalakirBloodwitchEffect copy() {
        return new MalakirBloodwitchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        FilterControlledPermanent filter = new FilterControlledPermanent("Vampire");
        filter.add(new SubtypePredicate("Vampire"));
        int amount = game.getBattlefield().countAll(filter, source.getControllerId(), game);
        Set<UUID> opponents = game.getOpponents(source.getControllerId());

        int total = 0;
        for (UUID opponentUuid : opponents) {
            Player opponent = game.getPlayer(opponentUuid);
            if (opponent != null) {
                total += opponent.loseLife(amount, game);
            }
            if (total > 0) {
                player.gainLife(total, game);
            }
        }

        return true;
    }
}
