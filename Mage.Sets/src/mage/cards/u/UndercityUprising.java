package mage.cards.u;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndercityUprising extends CardImpl {

    public UndercityUprising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{G}");

        // Creatures you control gain deathtouch until end of turn. Then target creature you control fights target creature you don't control.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .setText("Then target creature you control fights target creature you don't control"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private UndercityUprising(final UndercityUprising card) {
        super(card);
    }

    @Override
    public UndercityUprising copy() {
        return new UndercityUprising(this);
    }
}
