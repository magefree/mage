package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BogwaterLumaret extends CardImpl {

    public BogwaterLumaret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.FROG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature or another creature you control enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_PERMANENT_CREATURE, false, true
        ));
    }

    private BogwaterLumaret(final BogwaterLumaret card) {
        super(card);
    }

    @Override
    public BogwaterLumaret copy() {
        return new BogwaterLumaret(this);
    }
}
