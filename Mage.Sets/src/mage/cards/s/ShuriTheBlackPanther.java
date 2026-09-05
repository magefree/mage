package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ShuriTheBlackPanther extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterControlledArtifactPermanent("you control six or more artifacts"),
        ComparisonType.OR_GREATER, 6
    );

    public ShuriTheBlackPanther(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Shuri attacks, draw a card if you control three or more artifacts. Then if you control six or more artifacts, creatures you control get +2/+2 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new ConditionalOneShotEffect(
            new DrawCardSourceControllerEffect(1),
            MetalcraftCondition.instance,
            "draw a card if you control three or more artifacts"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
            new BoostControlledEffect(2, 2, Duration.EndOfTurn),
            condition,
            "Then if you control six or more artifacts, creatures you control get +2/+2 until end of turn"
        ));
        this.addAbility(ability);
    }

    private ShuriTheBlackPanther(final ShuriTheBlackPanther card) {
        super(card);
    }

    @Override
    public ShuriTheBlackPanther copy() {
        return new ShuriTheBlackPanther(this);
    }
}
