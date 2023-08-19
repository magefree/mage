package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ChancellorOfTales extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("an Adventure spell");

    static {
        filter.add(SubType.ADVENTURE.getPredicate());
    }
    
    public ChancellorOfTales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an Adventure spell, you may copy it. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetSpellEffect(true).withSpellName("it"),
                filter, true, SetTargetPointer.SPELL
        ));
    }

    private ChancellorOfTales(final ChancellorOfTales card) {
        super(card);
    }

    @Override
    public ChancellorOfTales copy() {
        return new ChancellorOfTales(this);
    }
}
