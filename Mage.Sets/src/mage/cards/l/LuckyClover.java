package mage.cards.l;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LuckyClover extends CardImpl {

    private static final FilterSpell filter
            = new FilterInstantOrSorcerySpell("an Adventure instant or sorcery spell");

    static {
        filter.add(SubType.ADVENTURE.getPredicate());
    }

    public LuckyClover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever you cast an Adventure instant or sorcery spell, copy it. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetSpellEffect(true).withSpellName("it"),
                filter, false, SetTargetPointer.SPELL
        ));
    }

    private LuckyClover(final LuckyClover card) {
        super(card);
    }

    @Override
    public LuckyClover copy() {
        return new LuckyClover(this);
    }
}
