package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MathiseSurgeChanneler extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("an instant or sorcery spell with mana value 3 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public MathiseSurgeChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast an instant or sorcery spell with mana value 3 or greater, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(20);
        this.addAbility(new SpellCastControllerTriggeredAbility(effect, filter, false, SetTargetPointer.SPELL));

        // 1-9 | Each player draws a card.
        effect.addTableEntry(1, 9, new DrawCardAllEffect(1));

        // 10-19 | You draw a card.
        effect.addTableEntry(10, 19, new DrawCardSourceControllerEffect(1, true));

        // 20 | Copy that spell. You may choose new targets for the copy.
        effect.addTableEntry(20, 20, new CopyTargetStackObjectEffect(true));
    }

    private MathiseSurgeChanneler(final MathiseSurgeChanneler card) {
        super(card);
    }

    @Override
    public MathiseSurgeChanneler copy() {
        return new MathiseSurgeChanneler(this);
    }
}
