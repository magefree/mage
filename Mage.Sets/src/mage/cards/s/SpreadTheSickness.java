package mage.cards.s;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SpreadTheSickness extends CardImpl {

    public SpreadTheSickness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Destroy target creature, then proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new ProliferateEffect().concatBy(", then"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SpreadTheSickness(final SpreadTheSickness card) {
        super(card);
    }

    @Override
    public SpreadTheSickness copy() {
        return new SpreadTheSickness(this);
    }

}
