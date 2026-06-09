package mage.cards.v;

import mage.abilities.common.SourceDealsNoncombatDamageToOpponentTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VirtueOfCourage extends AdventureCard {

    public VirtueOfCourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}",
                "Embereth Blaze",
                new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Virtue of Courage
        // Whenever a source you control deals noncombat damage to an opponent, you may exile that many cards from the top of your library. You may play those cards this turn.
        this.getLeftHalfCard().addAbility(new SourceDealsNoncombatDamageToOpponentTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(SavedDamageValue.MANY, false, Duration.EndOfTurn)
                        .setText("you may exile that many cards from the top of your library. You may play those cards this turn.")
                , true, SetTargetPointer.NONE));

        // Embereth Blaze
        // Embereth Blaze deals 2 damage to any target.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTarget());

        finalizeCard();
    }

    private VirtueOfCourage(final VirtueOfCourage card) {
        super(card);
    }

    @Override
    public VirtueOfCourage copy() {
        return new VirtueOfCourage(this);
    }
}
