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
package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class FigureOfDestiny extends CardImpl {

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent();
    static {
        filter2.add(new SubtypePredicate(SubType.SPIRIT));
        filter3.add(new SubtypePredicate(SubType.WARRIOR));
    }

    public FigureOfDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/W}");
        this.subtype.add("Kithkin");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {RW}: Figure of Destiny becomes a Kithkin Spirit with base power and toughness 2/2.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new FigureOfDestiny.FigureOfDestinyToken1(), "", Duration.Custom),
                new ManaCostsImpl("{R/W}")));
        // {RW}{RW}{RW}: If Figure of Destiny is a Spirit, it becomes a Kithkin Spirit Warrior with base power and toughness 4/4.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                    new BecomesCreatureSourceEffect(new FigureOfDestiny.FigureOfDestinyToken2(), "", Duration.Custom),
                    new LockedInCondition(new SourceMatchesFilterCondition(filter2)),
                    "If {this} is a Spirit, it becomes a Kithkin Spirit Warrior with base power and toughness 4/4"),
                new ManaCostsImpl("{R/W}{R/W}{R/W}")                
                ));
        // {RW}{RW}{RW}{RW}{RW}{RW}: If Figure of Destiny is a Warrior, it becomes a Kithkin Spirit Warrior Avatar with base power and toughness 8/8, flying, and first strike.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                    new BecomesCreatureSourceEffect(new FigureOfDestiny.FigureOfDestinyToken3(), "", Duration.Custom),
                    new LockedInCondition(new SourceMatchesFilterCondition(filter3)),
                    "If {this} is a Warrior, it becomes a Kithkin Spirit Warrior Avatar with base power and toughness 8/8, flying, and first strike"),
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
            super("Figure of Destiny", "Kithkin Spirit with base power and toughness 2/2");
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
            super("Figure of Destiny", "Kithkin Spirit Warrior with base power and toughness 4/4");
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
            super("Figure of Destiny", "Kithkin Spirit Warrior Avatar with base power and toughness 8/8, flying, and first strike");
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
