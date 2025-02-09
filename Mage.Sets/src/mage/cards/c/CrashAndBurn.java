package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrashAndBurn extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VEHICLE, "Vehicle");

    public CrashAndBurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Choose one --
        // * Destroy target Vehicle.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // * Crash and Burn deals 6 damage to target creature or planeswalker.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(6)).addTarget(new TargetCreatureOrPlaneswalker()));
    }

    private CrashAndBurn(final CrashAndBurn card) {
        super(card);
    }

    @Override
    public CrashAndBurn copy() {
        return new CrashAndBurn(this);
    }
}
