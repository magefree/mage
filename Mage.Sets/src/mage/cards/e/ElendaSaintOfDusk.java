package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MoreThanStartingLifeTotalCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofFromInstantsAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class ElendaSaintOfDusk extends CardImpl {

    public ElendaSaintOfDusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Hexproof from instants
        this.addAbility(HexproofFromInstantsAbility.getInstance());

        // As long as your life total is greater than your starting life total, Elenda gets +1/+1 and has menace. Elenda gets an additional +5/+5 as long as your life total is at least 10 greater than your starting life total.
        Ability ability = new SimpleStaticAbility(
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                        MoreThanStartingLifeTotalCondition.ONE,
                        "As long as your life total is greater than your starting life total, {this} gets +1/+1"
                )
        );
        ability.addEffect(
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(new MenaceAbility()),
                        MoreThanStartingLifeTotalCondition.ONE,
                        "and has menace"
                )
        );
        ability.addEffect(
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(5, 5, Duration.WhileOnBattlefield),
                        MoreThanStartingLifeTotalCondition.TEN,
                        "{this} gets an additional +5/+5 as long as your life total is at least "
                                + "10 greater than your starting life total"
                )
        );
        this.addAbility(ability);
    }

    private ElendaSaintOfDusk(final ElendaSaintOfDusk card) {
        super(card);
    }

    @Override
    public ElendaSaintOfDusk copy() {
        return new ElendaSaintOfDusk(this);
    }
}
