package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Resistance extends CardImpl {

    public Resistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{W}");
        

        // Whenever a creature enters the battlefield under your control, Resistance deals 1 damage to each opponent.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT), StaticFilters.FILTER_PERMANENT_CREATURE, false));

        // {R}{W}: Target creature gains haste until end of turn and must attack or block this turn if able.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                        .setText("Target creature gains haste until end of turn"),
                new ManaCostsImpl<>("{R}{W}"));
        ability.addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfTurn).setText("and must attack"));
        //ability.addEffect(new GainAbilityTargetEffect(AttacksThisTurnMarkerAbility.getInstance(), Duration.EndOfTurn, "").setText(""));
        ability.addEffect(new BlocksIfAbleTargetEffect(Duration.EndOfTurn).setText("or block this turn if able"));
        //ability.addEffect(new GainAbilityTargetEffect(BlocksThisTurnMarkerAbility.getInstance(), Duration.EndOfTurn, "").setText(""));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Resistance(final Resistance card) {
        super(card);
    }

    @Override
    public Resistance copy() {
        return new Resistance(this);
    }
}
