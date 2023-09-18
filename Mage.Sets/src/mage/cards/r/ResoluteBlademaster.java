
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class ResoluteBlademaster extends CardImpl {

    public ResoluteBlademaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Rally</i>-Whenever Resolute Blademaster or another Ally enters the battlefield under your control, 
        // you control gain double strike until end of turn.
        Ability ability = new AllyEntersBattlefieldTriggeredAbility(
                new GainAbilityAllEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURES), false);
        this.addAbility(ability);
    }

    private ResoluteBlademaster(final ResoluteBlademaster card) {
        super(card);
    }

    @Override
    public ResoluteBlademaster copy() {
        return new ResoluteBlademaster(this);
    }
}
