
package mage.cards.f;

import java.util.UUID;
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
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public final class FigureOfDestiny extends CardImpl {

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent();
    static {
        filter2.add(new SubtypePredicate(SubType.SPIRIT));
        filter3.add(new SubtypePredicate(SubType.WARRIOR));
    }

    public FigureOfDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/W}");
        this.subtype.add(SubType.KITHKIN);

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

    private class FigureOfDestinyToken1 extends TokenImpl {

        public FigureOfDestinyToken1() {
            super("Figure of Destiny", "Kithkin Spirit with base power and toughness 2/2");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add(SubType.KITHKIN);
            this.subtype.add(SubType.SPIRIT);

            this.color.setRed(true);
            this.color.setWhite(true);
            this.power = new MageInt(2);
            this.toughness = new MageInt(2);
        }

        public FigureOfDestinyToken1(final FigureOfDestinyToken1 token) {
            super(token);
        }

        public FigureOfDestinyToken1 copy() {
            return new FigureOfDestinyToken1(this);
        }
    }

    private class FigureOfDestinyToken2 extends TokenImpl {

        public FigureOfDestinyToken2() {
            super("Figure of Destiny", "Kithkin Spirit Warrior with base power and toughness 4/4");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add(SubType.KITHKIN);
            this.subtype.add(SubType.SPIRIT);
            this.subtype.add(SubType.WARRIOR);

            this.color.setRed(true);
            this.color.setWhite(true);
            this.power = new MageInt(4);
            this.toughness = new MageInt(4);
        }
        public FigureOfDestinyToken2(final FigureOfDestinyToken2 token) {
            super(token);
        }

        public FigureOfDestinyToken2 copy() {
            return new FigureOfDestinyToken2(this);
        }

    }

    private class FigureOfDestinyToken3 extends TokenImpl {

        public FigureOfDestinyToken3() {
            super("Figure of Destiny", "Kithkin Spirit Warrior Avatar with base power and toughness 8/8, flying, and first strike");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add(SubType.KITHKIN);
            this.subtype.add(SubType.SPIRIT);
            this.subtype.add(SubType.WARRIOR);
            this.subtype.add(SubType.AVATAR);

            this.color.setRed(true);
            this.color.setWhite(true);
            this.power = new MageInt(8);
            this.toughness = new MageInt(8);
            this.addAbility(FlyingAbility.getInstance());
            this.addAbility(FirstStrikeAbility.getInstance());
        }

        public FigureOfDestinyToken3(final FigureOfDestinyToken3 token) {
            super(token);
        }

        public FigureOfDestinyToken3 copy() {
            return new FigureOfDestinyToken3(this);
        }
    }
}
