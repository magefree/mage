package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GrafMole extends CardImpl {

    public GrafMole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.MOLE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you sacrifice a Clue, you gain 3 life.
        this.addAbility(new SacrificePermanentTriggeredAbility(new GainLifeEffect(3), StaticFilters.FILTER_CONTROLLED_CLUE));
    }

    private GrafMole(final GrafMole card) {
        super(card);
    }

    @Override
    public GrafMole copy() {
        return new GrafMole(this);
    }
}
