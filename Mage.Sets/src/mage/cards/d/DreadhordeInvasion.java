package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author TheElk801
 */
public final class DreadhordeInvasion extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.ZOMBIE, "Zombie token you control with power 6 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 5));
        filter.add(TokenPredicate.TRUE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DreadhordeInvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // At the beginning of your upkeep, you lose 1 life and amass 1.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new LoseLifeSourceControllerEffect(1), TargetController.YOU, false
        );
        ability.addEffect(new AmassEffect(1, SubType.ZOMBIE).concatBy("and"));
        this.addAbility(ability);

        // Whenever a Zombie token you control with power 6 or greater attacks, it gains lifelink until end of turn.
        this.addAbility(new AttacksAllTriggeredAbility(
                new GainAbilityTargetEffect(
                        LifelinkAbility.getInstance(), Duration.EndOfTurn,
                        "it gains lifelink until end of turn"
                ), false, filter, SetTargetPointer.PERMANENT,
                false
        ));
    }

    private DreadhordeInvasion(final DreadhordeInvasion card) {
        super(card);
    }

    @Override
    public DreadhordeInvasion copy() {
        return new DreadhordeInvasion(this);
    }
}
