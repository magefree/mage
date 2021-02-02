package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class ConclavePhalanx extends CardImpl {

    public ConclavePhalanx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Convoke
        this.addAbility(new ConvokeAbility());
        // When Conclave Phalanx enters the battlefield, you gain 1 life for each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(
                new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE))));
    }

    private ConclavePhalanx(final ConclavePhalanx card) {
        super(card);
    }

    @Override
    public ConclavePhalanx copy() {
        return new ConclavePhalanx(this);
    }
}
