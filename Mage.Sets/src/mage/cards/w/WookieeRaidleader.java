
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class WookieeRaidleader extends CardImpl {

    public WookieeRaidleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R/G}{R/G}");
        this.subtype.add(SubType.WOOKIEE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Wookiee Raidleader attacks, antoher target creature gains trample until end of turn
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

    }

    private WookieeRaidleader(final WookieeRaidleader card) {
        super(card);
    }

    @Override
    public WookieeRaidleader copy() {
        return new WookieeRaidleader(this);
    }
}
