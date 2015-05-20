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
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class SoulOfInnistrad extends CardImpl {

    public SoulOfInnistrad(UUID ownerId) {
        super(ownerId, 115, "Soul of Innistrad", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "M15";
        this.subtype.add("Avatar");

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // {3}{B}{B}: Return up to three target creature cards from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl("{3}{B}{B}"));
        ability.addTarget(new TargetCardInYourGraveyard(0, 3, new FilterCreatureCard("creature cards from your graveyard")));
        this.addAbility(ability);

        // {3}{B}{B}, Exile Soul of Innistrad from your graveyard: Return up to three target creature cards from your graveyard to your hand.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl("{3}{B}{B}"));
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetCardInYourGraveyard(0, 3, new FilterCreatureCard("creature cards from your graveyard")));
        this.addAbility(ability);  

    }

    public SoulOfInnistrad(final SoulOfInnistrad card) {
        super(card);
    }

    @Override
    public SoulOfInnistrad copy() {
        return new SoulOfInnistrad(this);
    }
}
