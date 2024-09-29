package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EscarpmentFortress extends CardImpl {

    public EscarpmentFortress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Other creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, true
        )));

        // Whenever you attack with two or more creatures, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DrawCardSourceControllerEffect(1), 2
        ));
    }

    private EscarpmentFortress(final EscarpmentFortress card) {
        super(card);
    }

    @Override
    public EscarpmentFortress copy() {
        return new EscarpmentFortress(this);
    }
}
