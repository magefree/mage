package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JudithTheScourgeDiva extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("nontoken creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(TokenPredicate.instance));
    }

    public JudithTheScourgeDiva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, true)
        ));

        // Whenever a nontoken creature you control dies, Judith, the Scourge Diva deals 1 damage to any target.
        Ability ability = new DiesCreatureTriggeredAbility(new DamageTargetEffect(1), false, filter);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private JudithTheScourgeDiva(final JudithTheScourgeDiva card) {
        super(card);
    }

    @Override
    public JudithTheScourgeDiva copy() {
        return new JudithTheScourgeDiva(this);
    }
}
