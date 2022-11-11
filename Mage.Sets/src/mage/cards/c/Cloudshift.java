package mage.cards.c;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class Cloudshift extends CardImpl {

    public Cloudshift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target creature you control, then return that card to the battlefield under your control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileTargetForSourceEffect());
        this.getSpellAbility().addEffect(new ReturnToBattlefieldUnderYourControlTargetEffect().concatBy(", then"));
    }

    private Cloudshift(final Cloudshift card) {
        super(card);
    }

    @Override
    public Cloudshift copy() {
        return new Cloudshift(this);
    }
}
