package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class PacksFavor extends CardImpl {

    public PacksFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().addEffect(
                new BoostTargetEffect(3, 3, Duration.EndOfTurn)
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PacksFavor(final PacksFavor card) {
        super(card);
    }

    @Override
    public PacksFavor copy() {
        return new PacksFavor(this);
    }
}
