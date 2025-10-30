package mage.cards.m;

import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MorningtidesLight extends CardImpl {

    public MorningtidesLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Exile any number of target creatures. At the beginning of the next end step, return those cards to the battlefield tapped under their owners' control.
        this.getSpellAbility().addEffect(new ExileReturnBattlefieldNextEndStepTargetEffect()
                .setText("exile any number of target creatures. At the beginning of the next end step, " +
                        "return those cards to the battlefield tapped under their owners' control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));

        // Until your next turn, prevent all damage that would be dealt to you.
        this.getSpellAbility().addEffect(new PreventDamageToControllerEffect(Duration.UntilYourNextTurn)
                .setText("<br>Until your next turn, prevent all damage that would be dealt to you"));

        // Exile Morningtide's Light.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private MorningtidesLight(final MorningtidesLight card) {
        super(card);
    }

    @Override
    public MorningtidesLight copy() {
        return new MorningtidesLight(this);
    }
}
