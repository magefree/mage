package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class LumberingSatyr extends CardImpl {

    public LumberingSatyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // All creatures have forestwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new GainAbilityAllEffect(new ForestwalkAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_CREATURES)));
    }

    private LumberingSatyr(final LumberingSatyr card) {
        super(card);
    }

    @Override
    public LumberingSatyr copy() {
        return new LumberingSatyr(this);
    }
}
