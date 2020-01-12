package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PouncingLynx extends CardImpl {

    public PouncingLynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As long as it's your turn, Pouncing Lynx has first strike.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                FirstStrikeAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), MyTurnCondition.instance,
                        "As long as it's your turn, "
                                + "{this} has first strike."
                )
        ).addHint(MyTurnHint.instance));
    }

    private PouncingLynx(final PouncingLynx card) {
        super(card);
    }

    @Override
    public PouncingLynx copy() {
        return new PouncingLynx(this);
    }
}
