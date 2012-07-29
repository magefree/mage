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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author jeffwadsworth
 */
public class DragonmasterOutcast extends CardImpl<DragonmasterOutcast> {

    private static final FilterPermanent filter = new FilterPermanent("land");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public DragonmasterOutcast(UUID ownerId) {
        super(ownerId, 81, "Dragonmaster Outcast", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, if you control six or more lands, put a 5/5 red Dragon creature token with flying onto the battlefield.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new DragonToken(), 1), Constants.TargetController.YOU, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, new ControlsPermanentCondition(filter, ControlsPermanentCondition.CountType.MORE_THAN, 5), "At the beginning of your upkeep, if you control six or more lands, put a 5/5 red Dragon creature token with flying onto the battlefield."));
    }

    public DragonmasterOutcast(final DragonmasterOutcast card) {
        super(card);
    }

    @Override
    public DragonmasterOutcast copy() {
        return new DragonmasterOutcast(this);
    }
}

class DragonToken extends Token {

    public DragonToken() {
        super("Dragon", "5/5 red Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.RED;
        subtype.add("Dragon");
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
    }
}
