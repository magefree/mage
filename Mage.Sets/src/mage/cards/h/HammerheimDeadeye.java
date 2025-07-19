
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HammerheimDeadeye extends CardImpl {

    public HammerheimDeadeye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Echo {5}{R}
        this.addAbility(new EchoAbility("{5}{R}"));

        // When Hammerheim Deadeye enters the battlefield, destroy target creature with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        Target target = new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private HammerheimDeadeye(final HammerheimDeadeye card) {
        super(card);
    }

    @Override
    public HammerheimDeadeye copy() {
        return new HammerheimDeadeye(this);
    }
}
