package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class SolariumSentry extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 2 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 2));
    }

    public SolariumSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an opponent casts a spell with mana value 2 or less, you gain 2 life.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
            new GainLifeEffect(2), filter, false
        ));
    }

    private SolariumSentry(final SolariumSentry card) {
        super(card);
    }

    @Override
    public SolariumSentry copy() {
        return new SolariumSentry(this);
    }
}
