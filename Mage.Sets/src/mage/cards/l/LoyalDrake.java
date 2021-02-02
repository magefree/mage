package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.CommanderInPlayCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author TheElk801
 */
public final class LoyalDrake extends CardImpl {

    public LoyalDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lieutenant — At the beginning of combat on your turn, if you control your commander, draw a card.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new DrawCardSourceControllerEffect(1),
                        TargetController.YOU, false
                ), CommanderInPlayCondition.instance,
                "<i>Lieutenant</i> &mdash; At the beginning of combat "
                + "on your turn, if you control your commander, draw a card."
        ));
    }

    private LoyalDrake(final LoyalDrake card) {
        super(card);
    }

    @Override
    public LoyalDrake copy() {
        return new LoyalDrake(this);
    }
}
