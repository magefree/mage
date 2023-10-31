package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class OgreBattledriver extends CardImpl {

    public OgreBattledriver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another creature enters the battlefield under your control, that creature gets +2/+0 and gains haste until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(2, 0, Duration.EndOfTurn).setText("that creature gets +2/+0"),
                StaticFilters.FILTER_ANOTHER_CREATURE, false, SetTargetPointer.PERMANENT);
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains haste until end of turn"));
        this.addAbility(ability);
        
    }

    private OgreBattledriver(final OgreBattledriver card) {
        super(card);
    }

    @Override
    public OgreBattledriver copy() {
        return new OgreBattledriver(this);
    }
}
