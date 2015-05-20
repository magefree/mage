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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class Kindle extends CardImpl {

    private static final FilterCard filter = new FilterCard("2 plus the number of cards named Kindle");

    static {
        filter.add(new NamePredicate("Kindle"));
    }

    public Kindle(UUID ownerId) {
        super(ownerId, 184, "Kindle", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "TMP";


        // Kindle deals X damage to target creature or player, where X is 2 plus the number of cards named Kindle in all graveyards.
        Effect effect = new DamageTargetEffect(new KindleCardsInAllGraveyardsCount(filter));
        effect.setText("{this} deals X damage to target creature or player, where X is 2 plus the number of cards named Kindle in all graveyards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public Kindle(final Kindle card) {
        super(card);
    }

    @Override
    public Kindle copy() {
        return new Kindle(this);
    }
}

class KindleCardsInAllGraveyardsCount implements DynamicValue {

    private final FilterCard filter;

    public KindleCardsInAllGraveyardsCount(FilterCard filter) {
        this.filter = filter;
    }

    private KindleCardsInAllGraveyardsCount(KindleCardsInAllGraveyardsCount dynamicValue) {
        this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        PlayerList playerList = game.getPlayerList();
        for (UUID playerUUID : playerList) {
            Player player = game.getPlayer(playerUUID);
            if (player != null) {
                amount += player.getGraveyard().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
            }
        }
        return amount + 2;
    }

    @Override
    public KindleCardsInAllGraveyardsCount copy() {
        return new KindleCardsInAllGraveyardsCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return filter.getMessage() + " in all graveyards";
    }
}
