package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RowansBattleguard extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPlaneswalkerPermanent(SubType.ROWAN);
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter);

    public RowansBattleguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // As long as you control a Rowan planeswalker, Rowan's Battleguard gets +3/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield), condition,
                "As long as you control a Rowan planeswalker, {this} gets +3/+0"
        )));
    }

    private RowansBattleguard(final RowansBattleguard card) {
        super(card);
    }

    @Override
    public RowansBattleguard copy() {
        return new RowansBattleguard(this);
    }
}
