
package mage.cards.a;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.command.emblems.ArlinnEmbracedByTheMoonEmblem;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class ArlinnEmbracedByTheMoon extends CardImpl {

    public ArlinnEmbracedByTheMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARLINN);
        this.color.setRed(true);
        this.color.setGreen(true);

        this.nightCard = true;

        // +1: Creatures you control get +1/+1 and gain trample until end of turn.
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE);
        effect.setText("Creatures you control get +1/+1");
        LoyaltyAbility ability = new LoyaltyAbility(effect, 1);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE);
        effect.setText("and gain trample until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -1: Arlinn, Embraced by the Moon deals 3 damage to any target. Transform Arlinn, Embraced by the Moon.
        this.addAbility(new TransformAbility());
        ability = new LoyaltyAbility(new DamageTargetEffect(3), -1);
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new TransformSourceEffect());
        this.addAbility(ability);

        // -6: You get an emblem with "Creatures you control have haste and '{T}: This creature deals damage equal to its power to any target.'"
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ArlinnEmbracedByTheMoonEmblem()), -6));
    }

    private ArlinnEmbracedByTheMoon(final ArlinnEmbracedByTheMoon card) {
        super(card);
    }

    @Override
    public ArlinnEmbracedByTheMoon copy() {
        return new ArlinnEmbracedByTheMoon(this);
    }
}
