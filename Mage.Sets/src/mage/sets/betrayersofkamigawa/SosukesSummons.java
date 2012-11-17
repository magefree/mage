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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.common.CreatureEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SnakeToken;

/**
 *
 * @author LevelX2
 */
public class SosukesSummons extends CardImpl<SosukesSummons> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken Snake");

    static {
            filter.add(new SubtypePredicate("Snake"));
            filter.add(Predicates.not(new TokenPredicate()));
    }

    public SosukesSummons(UUID ownerId) {
        super(ownerId, 145, "Sosuke's Summons", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "BOK";

        this.color.setGreen(true);

        // Put two 1/1 green Snake creature tokens onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SnakeToken(), 2));

        // Whenever a nontoken Snake enters the battlefield under your control, you may return Sosuke's Summons from your graveyard to your hand.
        this.addAbility(new CreatureEntersBattlefieldTriggeredAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), filter, true, false));


    }

    public SosukesSummons(final SosukesSummons card) {
        super(card);
    }

    @Override
    public SosukesSummons copy() {
        return new SosukesSummons(this);
    }
}
