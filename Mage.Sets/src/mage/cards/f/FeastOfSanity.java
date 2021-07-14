package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeastOfSanity extends CardImpl {

    public FeastOfSanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever you discard a card, Feast of Sanity deals 1 damage to any target and you gain 1 life.
        Ability ability = new DiscardCardControllerTriggeredAbility(new DamageTargetEffect(1), false);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FeastOfSanity(final FeastOfSanity card) {
        super(card);
    }

    @Override
    public FeastOfSanity copy() {
        return new FeastOfSanity(this);
    }
}
