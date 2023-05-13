package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaldinCenturyHerdmaster extends CardImpl {

    public BaldinCenturyHerdmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(7);

        // Sumo Spirit—As long as it's your turn, each creature assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new CombatDamageByToughnessEffect(StaticFilters.FILTER_PERMANENT_CREATURE, false),
                MyTurnCondition.instance, "as long as it's your turn, each creature " +
                "assigns combat damage equal to its toughness rather than its power"
        )).withFlavorWord("Sumo Spirit"));

        // Hundred Hand Slap—Whenever E. Honda, Sumo Champion attacks, up to one hundred target creatures each get +0/+X until end of turn, where X is the number of cards in your hand.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(
                StaticValue.get(0), CardsInControllerHandCount.instance, Duration.EndOfTurn
        ).setText("up to one hundred target creatures each get +0/+X until end of turn, where X is the number of cards in your hand"));
        ability.addTarget(new TargetCreaturePermanent(0, 100));
        this.addAbility(ability.withFlavorWord("Hundred Hand Slap"));
    }

    private BaldinCenturyHerdmaster(final BaldinCenturyHerdmaster card) {
        super(card);
    }

    @Override
    public BaldinCenturyHerdmaster copy() {
        return new BaldinCenturyHerdmaster(this);
    }
}
