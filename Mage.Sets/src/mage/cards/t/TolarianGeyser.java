package mage.cards.t;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TolarianGeyser extends CardImpl {

    public TolarianGeyser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Kicker {W}
        this.addAbility(new KickerAbility("{W}"));

        // Return target creature to its owner's hand. Draw a card. If this spell was kicked, you gain 3 life.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(3), KickedCondition.ONCE,
                "If this spell was kicked, you gain 3 life"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TolarianGeyser(final TolarianGeyser card) {
        super(card);
    }

    @Override
    public TolarianGeyser copy() {
        return new TolarianGeyser(this);
    }
}
