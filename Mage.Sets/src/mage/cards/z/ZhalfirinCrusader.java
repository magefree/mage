
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.common.RedirectDamageFromSourceToTargetEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class ZhalfirinCrusader extends CardImpl {

    public ZhalfirinCrusader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN, SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());

        // {1}{W}: The next 1 damage that would be dealt to Zhalfirin Crusader this turn is dealt to any target instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RedirectDamageFromSourceToTargetEffect(Duration.EndOfTurn, 1, RedirectionEffect.UsageType.ONE_USAGE_ABSOLUTE), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ZhalfirinCrusader(final ZhalfirinCrusader card) {
        super(card);
    }

    @Override
    public ZhalfirinCrusader copy() {
        return new ZhalfirinCrusader(this);
    }
}
