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

package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class BloodSpeaker extends CardImpl<BloodSpeaker> {

    private static final FilterControlledCreaturePermanent filter = new  FilterControlledCreaturePermanent("a Demon under your control");
    private static final FilterCard filterCard = new  FilterCard("Demon card");
    static {
        filter.add(new SubtypePredicate("Demon"));
        filterCard.add(new SubtypePredicate("Demon"));
    }

    public BloodSpeaker (UUID ownerId) {
        super(ownerId, 103, "Blood Speaker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Ogre");
        this.subtype.add("Shaman");
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, you may sacrifice Blood Speaker. If you do, search your library for a Demon card, reveal that card, and put it into your hand. Then shuffle your library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), Constants.TargetController.YOU, true);
        ability.addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true, true, "If you do, search your library for "));
        this.addAbility(ability);

        // Whenever a Demon enters the battlefield under your control, return Blood Speaker from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), filter, false));
    }

    public BloodSpeaker (final BloodSpeaker card) {
        super(card);
    }

    @Override
    public BloodSpeaker copy() {
        return new BloodSpeaker(this);
    }

}
