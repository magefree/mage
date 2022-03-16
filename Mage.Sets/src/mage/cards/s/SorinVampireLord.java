package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SorinVampireLord extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.VAMPIRE, "");

    public SorinVampireLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SORIN);
        this.setStartingLoyalty(4);

        // +1: Up to one target creature gets +2/+0 until end of turn.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(2, 0), 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −2: Sorin, Vampire Lord deals 4 damage to any target. You gain 4 life.
        ability = new LoyaltyAbility(new DamageTargetEffect(4), -2);
        ability.addEffect(new GainLifeEffect(4).setText("You gain 4 life"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // −8: Until end of turn, each Vampire you control gains "{T}: Gain control of target creature."
        ability = new SimpleActivatedAbility(new GainControlTargetEffect(Duration.Custom), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new LoyaltyAbility(new GainAbilityControlledEffect(
                ability, Duration.EndOfTurn, filter
        ).setText("Until end of turn, each Vampire you control gains " +
                "\"{T}: Gain control of target creature.\""
        ), -8));
    }

    private SorinVampireLord(final SorinVampireLord card) {
        super(card);
    }

    @Override
    public SorinVampireLord copy() {
        return new SorinVampireLord(this);
    }
}
