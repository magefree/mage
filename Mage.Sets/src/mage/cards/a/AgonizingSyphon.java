package mage.cards.a;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgonizingSyphon extends CardImpl {

    public AgonizingSyphon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Agonizing Syphon deals 3 damage to any target and you gain 3 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(3).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private AgonizingSyphon(final AgonizingSyphon card) {
        super(card);
    }

    @Override
    public AgonizingSyphon copy() {
        return new AgonizingSyphon(this);
    }
}
