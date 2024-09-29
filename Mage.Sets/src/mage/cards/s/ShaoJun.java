package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShaoJun extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifacts you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ShaoJun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Leap Strike -- As long as it's your turn, Shao Jun has flying and first strike.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "as long as it's your turn, {this} has flying"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "and first strike."
        ));
        this.addAbility(ability.withFlavorWord("Leap Strike"));

        // Rope Dart -- Tap two untapped artifacts you control: Shao Jun deals 1 damage to each opponent.
        this.addAbility(new SimpleActivatedAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                new TapTargetCost(new TargetControlledPermanent(2, filter))
        ).withFlavorWord("Rope Dart"));
    }

    private ShaoJun(final ShaoJun card) {
        super(card);
    }

    @Override
    public ShaoJun copy() {
        return new ShaoJun(this);
    }
}
