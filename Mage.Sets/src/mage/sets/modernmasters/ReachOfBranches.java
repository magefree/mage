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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class ReachOfBranches extends CardImpl<ReachOfBranches> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Forest");
    static {
        filter.add(new SubtypePredicate("Forest"));
    }

    public ReachOfBranches(UUID ownerId) {
        super(ownerId, 158, "Reach of Branches", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{G}");
        this.expansionSetCode = "MMA";
        this.supertype.add("Tribal");
        this.subtype.add("Treefolk");

        this.color.setGreen(true);

        // Put a 2/5 green Treefolk Shaman creature token onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreefolkShamanToken()));
        // Whenever a Forest enters the battlefield under your control, you may return Reach of Branches from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),filter, true, "", true));
    }

    public ReachOfBranches(final ReachOfBranches card) {
        super(card);
    }

    @Override
    public ReachOfBranches copy() {
        return new ReachOfBranches(this);
    }
}

class TreefolkShamanToken extends Token {
    TreefolkShamanToken() {
        super("Treefolk Shaman", "2/5 green Treefolk Shaman creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Shaman");
        subtype.add("Rogue");
        power = new MageInt(2);
        toughness = new MageInt(5);
    }
}