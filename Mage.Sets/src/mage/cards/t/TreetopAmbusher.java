package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreetopAmbusher extends CardImpl {

    public TreetopAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Dash {1}{G}
        this.addAbility(new DashAbility("{1}{G}"));

        // Whenever Treetop Ambusher attacks, target creature you control gets +1/+1 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(
                1, 1, Duration.EndOfTurn
        ), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private TreetopAmbusher(final TreetopAmbusher card) {
        super(card);
    }

    @Override
    public TreetopAmbusher copy() {
        return new TreetopAmbusher(this);
    }
}
