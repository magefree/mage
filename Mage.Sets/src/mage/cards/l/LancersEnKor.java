
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.common.RedirectDamageFromSourceToTargetEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class LancersEnKor extends CardImpl {

    public LancersEnKor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {0}: The next 1 damage that would be dealt to Lancers en-Kor this turn is dealt to target creature you control instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RedirectDamageFromSourceToTargetEffect(Duration.EndOfTurn, 1, RedirectionEffect.UsageType.ONE_USAGE_ABSOLUTE), new GenericManaCost(0));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private LancersEnKor(final LancersEnKor card) {
        super(card);
    }

    @Override
    public LancersEnKor copy() {
        return new LancersEnKor(this);
    }
}
