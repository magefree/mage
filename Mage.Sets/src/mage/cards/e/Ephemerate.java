package mage.cards.e;

import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ephemerate extends CardImpl {

    public Ephemerate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target creature you control, then return it to the battlefield under its owner's control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, false));

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private Ephemerate(final Ephemerate card) {
        super(card);
    }

    @Override
    public Ephemerate copy() {
        return new Ephemerate(this);
    }
}
