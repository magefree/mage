package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author TheElk801
 */
public final class ResponseResurgence extends SplitCard {

    public ResponseResurgence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{R/W}{R/W}", "{3}{R}{W}", SpellAbilityType.SPLIT);

        // Response
        // Response deals 5 damage to target attacking or blocking creature.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new DamageTargetEffect(5)
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(
                new TargetAttackingOrBlockingCreature()
        );

        // Resurgence
        // Creatures you control gain first strike and vigilance until end of turn. After this main phase, there is an additional combat phase followed by an additional main phase.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new GainAbilityControlledEffect(
                        FirstStrikeAbility.getInstance(),
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("Creatures you control gain first strike")
        );
        this.getRightHalfCard().getSpellAbility().addEffect(
                new GainAbilityControlledEffect(
                        VigilanceAbility.getInstance(),
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("and vigilance until end of turn")
        );
        this.getRightHalfCard().getSpellAbility().addEffect(
                new AddCombatAndMainPhaseEffect()
        );
    }

    private ResponseResurgence(final ResponseResurgence card) {
        super(card);
    }

    @Override
    public ResponseResurgence copy() {
        return new ResponseResurgence(this);
    }
}
