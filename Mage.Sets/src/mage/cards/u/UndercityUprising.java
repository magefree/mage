package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class UndercityUprising extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public UndercityUprising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{G}");

        // Creatures you control gain deathtouch until end of turn. Then target creature you control fights target creature you don't control.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ));
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .setText("Then target creature you control fights target creature you don't control"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public UndercityUprising(final UndercityUprising card) {
        super(card);
    }

    @Override
    public UndercityUprising copy() {
        return new UndercityUprising(this);
    }
}
