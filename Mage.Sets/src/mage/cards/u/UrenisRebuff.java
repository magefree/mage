package mage.cards.u;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrenisRebuff extends CardImpl {

    public UrenisRebuff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Harmonize {5}{U}
        this.addAbility(new HarmonizeAbility(this, "{5}{U}"));
    }

    private UrenisRebuff(final UrenisRebuff card) {
        super(card);
    }

    @Override
    public UrenisRebuff copy() {
        return new UrenisRebuff(this);
    }
}
