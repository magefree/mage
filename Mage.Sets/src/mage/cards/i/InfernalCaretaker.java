package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandFromGraveyardAllEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author cg5
 */
public final class InfernalCaretaker extends CardImpl {

    private static FilterCard zombieCard = new FilterCard(SubType.ZOMBIE);

    public InfernalCaretaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Morph {3}{B}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{3}{B}")));

        // When Infernal Caretaker is turned face up, return all Zombie cards from all graveyards to their owners' hands.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new ReturnToHandFromGraveyardAllEffect(zombieCard)
                .setText("return all Zombie cards from all graveyards to their owners' hands")));
    }

    private InfernalCaretaker(final InfernalCaretaker card) {
        super(card);
    }

    @Override
    public InfernalCaretaker copy() {
        return new InfernalCaretaker(this);
    }
}
