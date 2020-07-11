package mage.cards.c;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChainToMemory extends CardImpl {

    public ChainToMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Target creature gets -4/-0 until end of turn. Scry 2.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, 0));
        this.getSpellAbility().addEffect(new ScryEffect(2, false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ChainToMemory(final ChainToMemory card) {
        super(card);
    }

    @Override
    public ChainToMemory copy() {
        return new ChainToMemory(this);
    }
}
