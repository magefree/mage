package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.AdventurePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GarenbrigSquire extends CardImpl {

    private static final FilterSpell filter
            = new FilterCreatureSpell("a creature spell that has an Adventure");

    static {
        filter.add(AdventurePredicate.instance);
    }

    public GarenbrigSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a creature spell that has an Adventure, Garenbrig Squire gets +1/+1 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter, false
        ));
    }

    private GarenbrigSquire(final GarenbrigSquire card) {
        super(card);
    }

    @Override
    public GarenbrigSquire copy() {
        return new GarenbrigSquire(this);
    }
}
