package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ColossusSteelStalwart extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Mutants you control");

    static {
        filter.add(SubType.MUTANT.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ColossusSteelStalwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // During your turn, Colossus has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(IndestructibleAbility.getInstance()),
            MyTurnCondition.instance,
            "During your turn, {this} has indestructible"
        )).addHint(MyTurnHint.instance));

        // Other Mutants you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    private ColossusSteelStalwart(final ColossusSteelStalwart card) {
        super(card);
    }

    @Override
    public ColossusSteelStalwart copy() {
        return new ColossusSteelStalwart(this);
    }
}
