package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterHistoricSpell;

import java.util.UUID;

public final class SerraDisciple extends CardImpl {

    private static final FilterSpell filter = new FilterHistoricSpell();

    public SerraDisciple(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        subtype.add(SubType.BIRD, SubType.CLERIC);
        power = new MageInt(1);
        toughness = new MageInt(1);

        // Flying, first strike
        addAbility(FlyingAbility.getInstance());
        addAbility(FirstStrikeAbility.getInstance());

        // Whenever you cast a historic spell, Serra Disciple gets +1/+1 until end of turn
        addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter, false
        ));
    }

    public SerraDisciple(final SerraDisciple serraDisciple) {
        super(serraDisciple);
    }

    public SerraDisciple copy() {
        return new SerraDisciple(this);
    }
}
