package mage.cards.c;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CollectiveNightmare extends CardImpl {

    public CollectiveNightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Target creature gets -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CollectiveNightmare(final CollectiveNightmare card) {
        super(card);
    }

    @Override
    public CollectiveNightmare copy() {
        return new CollectiveNightmare(this);
    }
}
