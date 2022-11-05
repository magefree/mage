package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DrewTwoOrMoreCardsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrenchStalker extends CardImpl {

    public TrenchStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // As long as you've drawn two or more cards this turn, Trench Stalker has deathtouch and lifelink.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield),
                DrewTwoOrMoreCardsCondition.instance, "As long as you've drawn two or more cards this turn, {this} has deathtouch"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield),
                DrewTwoOrMoreCardsCondition.instance, "and lifelink"
        ));
        this.addAbility(ability.addHint(CardsDrawnThisTurnDynamicValue.getHint()));
    }

    private TrenchStalker(final TrenchStalker card) {
        super(card);
    }

    @Override
    public TrenchStalker copy() {
        return new TrenchStalker(this);
    }
}
