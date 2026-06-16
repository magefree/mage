package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class PatriotYoungAvenger extends CardImpl {

    public PatriotYoungAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // As long as Patriot is equipped, other creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, true),
            EquippedSourceCondition.instance,
            "as long as {this} is equipped, other creatures you control get +1/+0"
        )));
    }

    private PatriotYoungAvenger(final PatriotYoungAvenger card) {
        super(card);
    }

    @Override
    public PatriotYoungAvenger copy() {
        return new PatriotYoungAvenger(this);
    }
}
