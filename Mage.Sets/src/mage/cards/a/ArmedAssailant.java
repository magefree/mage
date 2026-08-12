package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ArmedAssailant extends CardImpl {

    private static final String rule1 = "As long as {this} is equipped, it gets +2/+0";
    private static final String rule2 = "As long as {this} is equipped, it has menace";

    public ArmedAssailant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // As long as this creature is equipped, it gets +2/+0 and has menace.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield), EquippedSourceCondition.instance, rule1));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(new MenaceAbility()).setText("and has menace"), EquippedSourceCondition.instance, rule2));
        this.addAbility(ability);
    }

    private ArmedAssailant(final ArmedAssailant card) {
        super(card);
    }

    @Override
    public ArmedAssailant copy() {
        return new ArmedAssailant(this);
    }
}
