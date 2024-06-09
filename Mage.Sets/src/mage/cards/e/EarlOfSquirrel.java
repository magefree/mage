package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.SquirrellinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class EarlOfSquirrel extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creature tokens you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Squirrels");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(SubType.SQUIRREL.getPredicate());
    }

    public EarlOfSquirrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Squirrellink (Damage dealt by this creature also causes you to create that many 1/1 green Squirrel creature tokens.)
        this.addAbility(SquirrellinkAbility.getInstance());

        // Creature tokens you control are Squirrels in addition to their other creature types.
        this.addAbility(new SimpleStaticAbility(new BecomesSubtypeAllEffect(
                Duration.WhileOnBattlefield, Arrays.asList(SubType.SQUIRREL), filter, false
        ).setText("Creature tokens you control are Squirrels in addition to their other creature types")));

        // Other Squirrels you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter2, true
        )));
    }

    private EarlOfSquirrel(final EarlOfSquirrel card) {
        super(card);
    }

    @Override
    public EarlOfSquirrel copy() {
        return new EarlOfSquirrel(this);
    }
}
