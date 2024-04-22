package mage.cards.s;

import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.util.functions.RemoveTypeCopyApplier;

import java.util.UUID;

/**
 * @author alexander-novo
 */
public final class StormOfSaruman extends CardImpl {

    public StormOfSaruman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ENCHANTMENT }, "{4}{U}{U}");

        // Ward {3}
        this.addAbility(new WardAbility(new GenericManaCost(3), false));

        // Whenever you cast your second spell each turn, copy it, except the copy isn't legendary. You may choose new targets for the copy.
        this.addAbility(new CastSecondSpellTriggeredAbility(Zone.BATTLEFIELD,
                new CopyTargetSpellEffect(false, true, true, 1, new RemoveTypeCopyApplier(SuperType.LEGENDARY))
                        .setText("copy it, except the copy isn't legendary. You may choose new targets for the copy."),
                TargetController.YOU, false,
                SetTargetPointer.SPELL));
    }

    private StormOfSaruman(final StormOfSaruman card) {
        super(card);
    }

    @Override
    public StormOfSaruman copy() {
        return new StormOfSaruman(this);
    }
}
