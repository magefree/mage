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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continious.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class AnthousaSetessanHero extends CardImpl<AnthousaSetessanHero> {

    public AnthousaSetessanHero(UUID ownerId) {
        super(ownerId, 149, "Anthousa, Setessan Hero", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "THS";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // <i>Heroic</i> - Whenever you cast a spell that targets Anthousa, Setessan Hero, up to three target lands you control each become 2/2 Warrior creatures until end of turn. They're still lands.
        Ability ability = new HeroicAbility(new BecomesCreatureTargetEffect(new AnthousaWarriorToken(), "lands", Duration.EndOfTurn));
        ability.addTarget(new TargetControlledPermanent(0,3,new FilterLandPermanent("lands"), false));
        this.addAbility(ability);
    }

    public AnthousaSetessanHero(final AnthousaSetessanHero card) {
        super(card);
    }

    @Override
    public AnthousaSetessanHero copy() {
        return new AnthousaSetessanHero(this);
    }
}

class AnthousaWarriorToken extends Token {

    public AnthousaWarriorToken() {
        super("", "2/2 Warrior creatures");
        cardType.add(CardType.CREATURE);
        subtype.add("Warrior");
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

}