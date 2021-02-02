
package mage.cards.c;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CauldronHaze extends CardImpl {

    private static final String rule = "Choose any number of target creatures. Each of those creatures gains persist until end of turn";

    public CauldronHaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W/B}");

        // Choose any number of target creatures. Each of those creatures gains persist until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new PersistAbility(), Duration.EndOfTurn, rule));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
    }

    private CauldronHaze(final CauldronHaze card) {
        super(card);
    }

    @Override
    public CauldronHaze copy() {
        return new CauldronHaze(this);
    }
}
