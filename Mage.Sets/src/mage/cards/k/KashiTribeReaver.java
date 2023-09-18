
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX
 */
public final class KashiTribeReaver extends CardImpl {

    public KashiTribeReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Kashi-Tribe Reaver deals combat damage to a creature, tap that creature and it doesn't untap during its controller's next untap step.
        Ability ability;
        ability = new DealsDamageToACreatureTriggeredAbility(new TapTargetEffect("tap that creature"), true, false, true);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("and it"));
        this.addAbility(ability);
        // {1}{G}: Regenerate Kashi-Tribe Reaver.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{G}")));
    }

    private KashiTribeReaver(final KashiTribeReaver card) {
        super(card);
    }

    @Override
    public KashiTribeReaver copy() {
        return new KashiTribeReaver(this);
    }
}