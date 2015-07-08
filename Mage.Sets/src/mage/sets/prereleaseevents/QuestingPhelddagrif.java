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
package mage.sets.prereleaseevents;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox

 */
public class QuestingPhelddagrif extends CardImpl {

    private static final FilterCard filter = new FilterCard("black and from red");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK), new ColorPredicate(ObjectColor.RED)));
    }

    public QuestingPhelddagrif(UUID ownerId) {
        super(ownerId, 13, "Questing Phelddagrif", Rarity.SPECIAL, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{G}");
        this.expansionSetCode = "PTC";
        this.subtype.add("Phelddagrif");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {G}: Questing Phelddagrif gets +1/+1 until end of turn. Target opponent puts a 1/1 green Hippo creature token onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn),
            new ManaCostsImpl("{G}"));
        ability.addEffect(new CreateTokenTargetEffect(new HippoToken()));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        // {W}: Questing Phelddagrif gains protection from black and from red until end of turn. Target opponent gains 2 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(new ProtectionAbility(filter),
            Duration.EndOfTurn), new ManaCostsImpl("{W}"));
        ability.addEffect(new GainLifeTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        // {U}: Questing Phelddagrif gains flying until end of turn. Target opponent may draw a card.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(),
            Duration.EndOfTurn), new ManaCostsImpl("{U}"));
        ability.addEffect(new DrawCardTargetEffect(1, true));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public QuestingPhelddagrif(final QuestingPhelddagrif card) {
        super(card);
    }

    @Override
    public QuestingPhelddagrif copy() {
        return new QuestingPhelddagrif(this);
    }
}

class HippoToken extends Token {
    public HippoToken() {
        super("Hippo", "1/1 green Hippo creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Hippo");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
