package mage.cards.o;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OraclesRestoration extends CardImpl {

    public OraclesRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Target creature you control gets +1/+1 until end of turn. You draw a card and gain 1 life.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1, true));
        this.getSpellAbility().addEffect(new GainLifeEffect(1).concatBy("and"));
    }

    private OraclesRestoration(final OraclesRestoration card) {
        super(card);
    }

    @Override
    public OraclesRestoration copy() {
        return new OraclesRestoration(this);
    }
}
