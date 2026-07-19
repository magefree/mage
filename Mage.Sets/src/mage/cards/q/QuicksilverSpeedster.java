package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author muz
 */
public final class QuicksilverSpeedster extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("spells");

    public QuicksilverSpeedster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // As long as Quicksilver is tapped, you may cast spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
            new ConditionalAsThoughEffect(
                new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter),
                SourceTappedCondition.TAPPED
            ).setText("as long as {this} is tapped, you may cast spells as though they had flash")
        ));
    }

    private QuicksilverSpeedster(final QuicksilverSpeedster card) {
        super(card);
    }

    @Override
    public QuicksilverSpeedster copy() {
        return new QuicksilverSpeedster(this);
    }
}
