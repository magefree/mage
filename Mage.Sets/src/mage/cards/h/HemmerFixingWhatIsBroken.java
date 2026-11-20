package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class HemmerFixingWhatIsBroken extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SPACECRAFT);

    public HemmerFixingWhatIsBroken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AENAR);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // During your turn, Spacecraft you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter),
            MyTurnCondition.instance, "During your turn, Spacecraft you control have hexproof"
        )));

        // Whenever Hemmer becomes tapped, draw a card, then discard a card.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));
    }

    private HemmerFixingWhatIsBroken(final HemmerFixingWhatIsBroken card) {
        super(card);
    }

    @Override
    public HemmerFixingWhatIsBroken copy() {
        return new HemmerFixingWhatIsBroken(this);
    }
}
