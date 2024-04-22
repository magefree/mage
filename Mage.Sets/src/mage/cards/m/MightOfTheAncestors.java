package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class MightOfTheAncestors extends CardImpl {

    public MightOfTheAncestors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // At the beginning of combat on your turn, target creature you control gets +2/+0 and gains vigilance until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                        .setText("target creature you control gets +2/+0"),
                TargetController.YOU, false
        );
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains vigilance until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

    }

    private MightOfTheAncestors(final MightOfTheAncestors card) {
        super(card);
    }

    @Override
    public MightOfTheAncestors copy() {
        return new MightOfTheAncestors(this);
    }
}
