
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author BursegSardaukar
 */
public final class KeeperOfKookus extends CardImpl {

    public KeeperOfKookus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}: Keeper of Kookus gains protection from red until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(ProtectionAbility.from(ObjectColor.RED), Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        this.addAbility(ability);
    }

    private KeeperOfKookus(final KeeperOfKookus card) {
        super(card);
    }

    @Override
    public KeeperOfKookus copy() {
        return new KeeperOfKookus(this);
    }
}