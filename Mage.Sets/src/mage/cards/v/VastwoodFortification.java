package mage.cards.v;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class VastwoodFortification extends ModalDoubleFacedCard {

    public VastwoodFortification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{G}",
                "Vastwood Thicket", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Vastwood Fortification
        // Instant

        // Put a +1/+1 counter on target creature.
        this.getLeftHalfCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // 2.
        // Vastwood Thicket
        // Land

        // Vastwood Thicket enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private VastwoodFortification(final VastwoodFortification card) {
        super(card);
    }

    @Override
    public VastwoodFortification copy() {
        return new VastwoodFortification(this);
    }
}
