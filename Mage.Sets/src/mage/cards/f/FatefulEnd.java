package mage.cards.f;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FatefulEnd extends CardImpl {

    public FatefulEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Fateful End deals 3 damage to any target. Scry 1.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3, true, "any target"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
    }

    private FatefulEnd(final FatefulEnd card) {
        super(card);
    }

    @Override
    public FatefulEnd copy() {
        return new FatefulEnd(this);
    }
}
