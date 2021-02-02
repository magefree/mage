
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ReadyWilling extends SplitCard {

    public ReadyWilling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{W}", "{1}{W}{B}", SpellAbilityType.SPLIT_FUSED);

        // Ready
        // Creatures you control are indestructible this turn. Untap each creature you control.
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures you controll"), false);
        effect.setText("Creatures you control are indestructible this turn");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addEffect(new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), "Untap each creature you control"));

        // Willing
        // Creatures you control gain deathtouch and lifelink until end of turn.
        getRightHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));
        effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("Creatures you control gain lifelink until end of turn");
        getRightHalfCard().getSpellAbility().addEffect(effect);

    }

    private ReadyWilling(final ReadyWilling card) {
        super(card);
    }

    @Override
    public ReadyWilling copy() {
        return new ReadyWilling(this);
    }

}
