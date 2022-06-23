
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import static mage.abilities.effects.RedirectionEffect.UsageType.ONE_USAGE_ABSOLUTE;
import mage.abilities.effects.common.RedirectDamageFromSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ZealousInquisitor extends CardImpl {

    public ZealousInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN, SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{W}: The next 1 damage that would be dealt to Zealous Inquisitor this turn is dealt to target creature instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RedirectDamageFromSourceToTargetEffect(Duration.EndOfTurn, 1, ONE_USAGE_ABSOLUTE), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ZealousInquisitor(final ZealousInquisitor card) {
        super(card);
    }

    @Override
    public ZealousInquisitor copy() {
        return new ZealousInquisitor(this);
    }
}
