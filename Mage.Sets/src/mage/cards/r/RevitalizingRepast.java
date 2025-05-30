package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RevitalizingRepast extends ModalDoubleFacedCard {

    public RevitalizingRepast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{B/G}",
                "Old-Growth Grove", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Revitalizing Repast
        // Instant

        // Put a +1/+1 counter on target creature. It gains indestructible until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                        .setText("It gains indestructible until end of turn")
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // 2.
        // Old-Growth Grove
        // Land

        // Old-Growth Grove enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B} or {G}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private RevitalizingRepast(final RevitalizingRepast card) {
        super(card);
    }

    @Override
    public RevitalizingRepast copy() {
        return new RevitalizingRepast(this);
    }
}
