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
package mage.sets.eventide;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class FigureOfDestiny extends CardImpl<FigureOfDestiny> {

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent();
    static {
        filter2.add(new SubtypePredicate("Spirit"));
        filter3.add(new SubtypePredicate("Warrior"));
    }

    public FigureOfDestiny(UUID ownerId) {
        super(ownerId, 139, "Figure of Destiny", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R/W}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Kithkin");

        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {RW}: Figure of Destiny becomes a 2/2 Kithkin Spirit.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new FigureOfDestiny.FigureOfDestinyToken1(), "", Duration.WhileOnBattlefield),
                new ManaCostsImpl("{R/W}")));
        // {RW}{RW}{RW}: If Figure of Destiny is a Spirit, it becomes a 4/4 Kithkin Spirit Warrior.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinousEffect(
                    new BecomesCreatureSourceEffect(new FigureOfDestiny.FigureOfDestinyToken2(), "", Duration.WhileOnBattlefield),
                    new SourceMatchesFilterCondition(filter2),
                    "If Figure of Destiny is a Spirit, it becomes a 4/4 Kithkin Spirit Warrior",
                    true),
                new ManaCostsImpl("{R/W}{R/W}{R/W}")                
                ));
        // {RW}{RW}{RW}{RW}{RW}{RW}: If Figure of Destiny is a Warrior, it becomes an 8/8 Kithkin Spirit Warrior Avatar with flying and first strike.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinousEffect(
                    new BecomesCreatureSourceEffect(new FigureOfDestiny.FigureOfDestinyToken3(), "", Duration.WhileOnBattlefield),
                    new SourceMatchesFilterCondition(filter3),
                    "If Figure of Destiny is a Warrior, it becomes an 8/8 Kithkin Spirit Warrior Avatar with flying and first strike",
                    true),
                new ManaCostsImpl("{R/W}{R/W}{R/W}{R/W}{R/W}{R/W}")                
                ));
    }

    public FigureOfDestiny(final FigureOfDestiny card) {
        super(card);
    }

    @Override
    public FigureOfDestiny copy() {
        return new FigureOfDestiny(this);
    }

    private class FigureOfDestinyToken1 extends Token {

        public FigureOfDestinyToken1() {
            super("Figure of Destiny", "2/2 Kithkin Spirit");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add("Kithkin");
            this.subtype.add("Spirit");

            this.color.setRed(true);
            this.color.setWhite(true);
            this.power = new MageInt(2);
            this.toughness = new MageInt(2);
        }
    }

    private class FigureOfDestinyToken2 extends Token {

        public FigureOfDestinyToken2() {
            super("Figure of Destiny", "4/4 Kithkin Spirit Warrior");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add("Kithkin");
            this.subtype.add("Spirit");
            this.subtype.add("Warrior");

            this.color.setRed(true);
            this.color.setWhite(true);
            this.power = new MageInt(4);
            this.toughness = new MageInt(4);
        }
    }

    private class FigureOfDestinyToken3 extends Token {

        public FigureOfDestinyToken3() {
            super("Figure of Destiny", "8/8 Kithkin Spirit Warrior Avatar with flying and first strike");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add("Kithkin");
            this.subtype.add("Spirit");
            this.subtype.add("Warrior");
            this.subtype.add("Avatar");

            this.color.setRed(true);
            this.color.setWhite(true);
            this.power = new MageInt(8);
            this.toughness = new MageInt(8);
            this.addAbility(FlyingAbility.getInstance());
            this.addAbility(FirstStrikeAbility.getInstance());
        }
    }
}
