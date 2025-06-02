package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.s.SerahFarron;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrystallizedSerah extends CardImpl {

    public CrystallizedSerah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.nightCard = true;
        this.color.setGreen(true);
        this.color.setWhite(true);

        // The first legendary creature spell you cast each turn costs {2} less to cast.
        this.addAbility(SerahFarron.makeAbility());

        // Legendary creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_LEGENDARY
        )));
    }

    private CrystallizedSerah(final CrystallizedSerah card) {
        super(card);
    }

    @Override
    public CrystallizedSerah copy() {
        return new CrystallizedSerah(this);
    }
}
