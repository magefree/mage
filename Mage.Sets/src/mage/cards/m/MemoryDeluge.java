package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MemoryDeluge extends CardImpl {

    public MemoryDeluge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Look at the top X cards of your library, where X is the amount of mana spent to cast this spell. Put two of them into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                ManaSpentToCastCount.instance, 2, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ).setText("look at the top X cards of your library, where X " +
                "is the amount of mana spent to cast this spell. Put two of them into your " +
                "hand and the rest on the bottom of your library in a random order"));

        // Flashback {5}{U}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{U}{U}")));
    }

    private MemoryDeluge(final MemoryDeluge card) {
        super(card);
    }

    @Override
    public MemoryDeluge copy() {
        return new MemoryDeluge(this);
    }
}
