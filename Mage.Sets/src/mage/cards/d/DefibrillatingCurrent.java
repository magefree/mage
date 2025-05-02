package mage.cards.d;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefibrillatingCurrent extends CardImpl {

    public DefibrillatingCurrent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2/R}{2/W}{2/B}");

        // Defibrillating Current deals 4 damage to target creature or planeswalker and you gain 2 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private DefibrillatingCurrent(final DefibrillatingCurrent card) {
        super(card);
    }

    @Override
    public DefibrillatingCurrent copy() {
        return new DefibrillatingCurrent(this);
    }
}
