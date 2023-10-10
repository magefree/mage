
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.common.RedirectDamageFromSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class WarriorEnKor extends CardImpl {

    public WarriorEnKor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {0}: The next 1 damage that would be dealt to Warrior en-Kor this turn is dealt to target creature you control instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new RedirectDamageFromSourceToTargetEffect(Duration.EndOfTurn, 1, RedirectionEffect.UsageType.ONE_USAGE_ABSOLUTE), new GenericManaCost(0));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private WarriorEnKor(final WarriorEnKor card) {
        super(card);
    }

    @Override
    public WarriorEnKor copy() {
        return new WarriorEnKor(this);
    }
}
