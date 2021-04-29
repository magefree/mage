package mage.cards.d;

import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Dispatch extends CardImpl {

    public Dispatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Tap target creature.
        this.getSpellAbility().addEffect(new TapTargetEffect());

        // Metalcraft — If you control three or more artifacts, exile that creature.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ExileTargetEffect(), MetalcraftCondition.instance,
                "<br><i>Metalcraft</i> &mdash; If you control three or more artifacts, exile that creature"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(MetalcraftHint.instance);
    }

    private Dispatch(final Dispatch card) {
        super(card);
    }

    @Override
    public Dispatch copy() {
        return new Dispatch(this);
    }

}
