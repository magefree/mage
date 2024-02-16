package mage.cards.s;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SyphonLife extends CardImpl {

    public SyphonLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Target player loses 2 life and you gain 2 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));

        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private SyphonLife(final SyphonLife card) {
        super(card);
    }

    @Override
    public SyphonLife copy() {
        return new SyphonLife(this);
    }
}
