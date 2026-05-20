package mage.cards.i;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImpracticalJoke extends CardImpl {

    public ImpracticalJoke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Damage can't be prevented this turn. Impractical Joke deals 3 damage to up to one target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageCantBePreventedEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker(0, 1));
    }

    private ImpracticalJoke(final ImpracticalJoke card) {
        super(card);
    }

    @Override
    public ImpracticalJoke copy() {
        return new ImpracticalJoke(this);
    }
}
