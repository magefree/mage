package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class EriettesTemptingApple extends CardImpl {

    public EriettesTemptingApple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOOD);

        // When Eriette's Tempting Apple enters the battlefield, gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn), false);
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn."));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}, {T}, Sacrifice Eriette's Tempting Apple: You gain 3 life.
        this.addAbility(new FoodAbility(true));

        // {2}, {T}, Sacrifice Eriette's Tempting Apple: Target opponent loses 3 life.
        ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(3), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        
    }

    private EriettesTemptingApple(final EriettesTemptingApple card) {
        super(card);
    }

    @Override
    public EriettesTemptingApple copy() {
        return new EriettesTemptingApple(this);
    }
}
