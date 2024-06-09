package mage.cards.t;

import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.watchers.common.DamagedByWatcher;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class TorchTheTower extends CardImpl {

    public TorchTheTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Bargain (You may sacrifice an artifact, enchantment, or token as you cast this spell.)
        this.addAbility(new BargainAbility());

        // Torch the Tower deals 2 damage to target creature or planeswalker. If this spell was bargained, instead it deals 3 damage to that permanent and you scry 1.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(3),
                new DamageTargetEffect(2),
                BargainedCondition.instance,
                "{this} deals 2 damage to target creature or planeswalker. "
                + "If this spell was bargained, instead it deals 3 damage to that permanent and you scry 1"
        ).addEffect(new ScryEffect(1)));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // If a permanent dealt damage by Torch the Tower would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn)
                .setText("<br>If a permanent dealt damage by {this} would die this turn, exile it instead"));
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private TorchTheTower(final TorchTheTower card) {
        super(card);
    }

    @Override
    public TorchTheTower copy() {
        return new TorchTheTower(this);
    }
}
