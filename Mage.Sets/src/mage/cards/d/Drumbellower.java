package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.UntapAllDuringEachOtherPlayersUntapStepEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Drumbellower extends CardImpl {

    public Drumbellower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Untap all creatures you control during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(new UntapAllDuringEachOtherPlayersUntapStepEffect(StaticFilters.FILTER_CONTROLLED_CREATURES)));
    }

    private Drumbellower(final Drumbellower card) {
        super(card);
    }

    @Override
    public Drumbellower copy() {
        return new Drumbellower(this);
    }
}
