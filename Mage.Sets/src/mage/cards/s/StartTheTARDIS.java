package mage.cards.s;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PlaneswalkEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StartTheTARDIS extends CardImpl {

    public StartTheTARDIS(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Surveil 2, then draw a card. You may planeswalk.
        this.getSpellAbility().addEffect(new SurveilEffect(2, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.getSpellAbility().addEffect(new PlaneswalkEffect(true));

        // Jump-start
        this.addAbility(new JumpStartAbility(this));
    }

    private StartTheTARDIS(final StartTheTARDIS card) {
        super(card);
    }

    @Override
    public StartTheTARDIS copy() {
        return new StartTheTARDIS(this);
    }
}
