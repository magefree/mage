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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.sets.bornofthegods.TokenAndCounters.GoldToken;
import mage.target.common.TargetCreaturePermanent;



/**
 *
 * @author LevelX2
 */
public class KingMacarTheGoldCursed extends CardImpl<KingMacarTheGoldCursed> {

    public KingMacarTheGoldCursed(UUID ownerId) {
        super(ownerId, 74, "King Macar, the Gold-Cursed", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "JOU";
        this.supertype.add("Legendary");
        this.subtype.add("Human");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Inspired - Whenever King Macar, the Gold-Cursed becomes untapped, you may exile target creature. If you do, put a colorless artifact token named Gold onto the battlefield. It has "Sacrifice this artifact: Add one mana of any color to your mana pool."
        Ability ability = new InspiredAbility(new ExileTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(true));
        Effect effect = new CreateTokenEffect(new GoldToken());
        effect.setText("If you do, put a colorless artifact token named Gold onto the battlefield. It has \"Sacrifice this artifact: Add one mana of any color to your mana pool.\"");
        ability.addEffect(effect);        
        this.addAbility(ability);                
    }

    public KingMacarTheGoldCursed(final KingMacarTheGoldCursed card) {
        super(card);
    }

    @Override
    public KingMacarTheGoldCursed copy() {
        return new KingMacarTheGoldCursed(this);
    }
}

//
//class GoldToken extends Token {
//
//    public GoldToken() {
//        super("Gold", "colorless artifact token named Gold onto the battlefield. It has \"Sacrifice this artifact: Add one mana of any color to your mana pool.\"");
//        this.setOriginalExpansionSetCode("BNG");
//        cardType.add(CardType.ARTIFACT);
//        subtype.add("Gold");
//
//        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new SacrificeSourceCost());
//        ability.addChoice(new ChoiceColor());
//        this.addAbility(ability);
//    }
//}