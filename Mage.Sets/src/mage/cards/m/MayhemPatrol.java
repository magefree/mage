package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MayhemPatrol extends CardImpl {

    public MayhemPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Mayhem Patrol attacks, target creature gets +1/+0 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(1, 0));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Blitz {1}{R}
        this.addAbility(new BlitzAbility(this, "{1}{R}"));
    }

    private MayhemPatrol(final MayhemPatrol card) {
        super(card);
    }

    @Override
    public MayhemPatrol copy() {
        return new MayhemPatrol(this);
    }
}
