package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTopCardPlayUntilExileAnotherEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SuperiorFoesOfSpiderMan extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 4));
    }

    public SuperiorFoesOfSpiderMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast a spell with mana value 4 or greater, you may exile the top card of your library. If you do, you may play that card until you exile another card with this creature.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ExileTopCardPlayUntilExileAnotherEffect(true),
                filter,
                true
        ));
    }

    private SuperiorFoesOfSpiderMan(final SuperiorFoesOfSpiderMan card) {
        super(card);
    }

    @Override
    public SuperiorFoesOfSpiderMan copy() {
        return new SuperiorFoesOfSpiderMan(this);
    }
}
