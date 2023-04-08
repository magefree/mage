package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SimonWildMagicSorcerer extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("an instant or sorcery spell with mana value 3 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public SimonWildMagicSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast an instant or sorcery spell with mana value 3 or greater, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(20);
        this.addAbility(new SpellCastControllerTriggeredAbility(effect, filter, false));

        // 1-9 | Each player draws a card.
        effect.addTableEntry(1, 9, new DrawCardAllEffect(1));

        // 10-19 | You draw a card.
        effect.addTableEntry(10, 19, new DrawCardSourceControllerEffect(1, "you"));

        // 20 | Copy that spell. You may choose new targets for the copy.
        effect.addTableEntry(20, 20, new CopyTargetSpellEffect());
    }

    private SimonWildMagicSorcerer(final SimonWildMagicSorcerer card) {
        super(card);
    }

    @Override
    public SimonWildMagicSorcerer copy() {
        return new SimonWildMagicSorcerer(this);
    }
}
