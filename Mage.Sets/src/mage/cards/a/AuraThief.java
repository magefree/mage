package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author awjackson
 */

public final class AuraThief extends CardImpl {
    
    public AuraThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aura Thief dies, you gain control of all enchantments. (You don't get to move Auras.)
        this.addAbility(new DiesSourceTriggeredAbility(
                new GainControlAllEffect(Duration.Custom, StaticFilters.FILTER_PERMANENT_ENCHANTMENTS)
                .setText("you gain control of all enchantments. <i>(You don't get to move Auras.)</i>")
        ));
    }

    private AuraThief(final AuraThief card) {
        super(card);
    }

    @Override
    public AuraThief copy() {
        return new AuraThief(this);
    }
}
