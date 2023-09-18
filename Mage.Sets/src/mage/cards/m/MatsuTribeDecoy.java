
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public final class MatsuTribeDecoy extends CardImpl {

    public MatsuTribeDecoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{G}: Target creature blocks Matsu-Tribe Decoy this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(), new ManaCostsImpl<>("{2}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Whenever Kashi-Tribe Reaver deals combat damage to a creature, tap that creature and it doesn't untap during its controller's next untap step.
        Ability ability2;
        ability2 = new DealsDamageToACreatureTriggeredAbility(new TapTargetEffect("tap that creature"), true, false, true);
        ability2.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("and it"));
        this.addAbility(ability2);
    }

    private MatsuTribeDecoy(final MatsuTribeDecoy card) {
        super(card);
    }

    @Override
    public MatsuTribeDecoy copy() {
        return new MatsuTribeDecoy(this);
    }
}
