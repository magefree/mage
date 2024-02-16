package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.SupportAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class GladehartCavalry extends CardImpl {

    public GladehartCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Gladehart Cavalry enters the battlefield, support 6.
        this.addAbility(new SupportAbility(this, 6));
        
        // Whenever a creature you control with a +1/+1 counter on it dies, you gain 2 life.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new GainLifeEffect(2),
                false,
                StaticFilters.FILTER_A_CONTROLLED_CREATURE_P1P1));
    }

    private GladehartCavalry(final GladehartCavalry card) {
        super(card);
    }

    @Override
    public GladehartCavalry copy() {
        return new GladehartCavalry(this);
    }
}
