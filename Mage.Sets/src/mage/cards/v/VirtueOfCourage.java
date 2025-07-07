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
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, new CardType[]{CardType.INSTANT}, "{3}{R}{R}", "Embereth Blaze", "{1}{R}");

        // Whenever a source you control deals noncombat damage to an opponent, you may exile that many cards from the top of your library. You may play those cards this turn.
        this.addAbility(new SourceDealsNoncombatDamageToOpponentTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(SavedDamageValue.MANY, false, Duration.EndOfTurn)
                        .setText("you may exile that many cards from the top of your library. You may play those cards this turn.")
                , true, SetTargetPointer.NONE));

        // Embereth Blaze
        // Embereth Blaze deals 2 damage to any target.
        this.getSpellCard().getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellCard().getSpellAbility().addTarget(new TargetAnyTarget());

        this.finalizeAdventure();
    }

    private VirtueOfCourage(final VirtueOfCourage card) {
        super(card);
    }

    @Override
    public VirtueOfCourage copy() {
        return new VirtueOfCourage(this);
    }
}
