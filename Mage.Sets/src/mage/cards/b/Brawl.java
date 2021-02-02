package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageWithPowerFromSourceToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Brawl extends CardImpl {

    public Brawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");

        // Until end of turn, all creatures gain "{T}: This creature deals damage equal to its power to target creature."
        Ability gainAbility = new SimpleActivatedAbility(new DamageWithPowerFromSourceToAnotherTargetEffect("This creature"), new TapSourceCost());
        gainAbility.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(
                new GainAbilityAllEffect(gainAbility, Duration.EndOfTurn, new FilterCreaturePermanent())
                        .setText("Until end of turn, all creatures gain \"{T}: This creature deals damage equal to its power to target creature.\"")
        );
    }

    private Brawl(final Brawl card) {
        super(card);
    }

    @Override
    public Brawl copy() {
        return new Brawl(this);
    }
}
