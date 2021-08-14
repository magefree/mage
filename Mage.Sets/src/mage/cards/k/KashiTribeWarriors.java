
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX
 */
public final class KashiTribeWarriors extends CardImpl {

    public KashiTribeWarriors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Kashi-Tribe Reaver deals combat damage to a creature, tap that creature and it doesn't untap during its controller's next untap step.
        Ability ability;
        ability = new DealsDamageToACreatureTriggeredAbility(new TapTargetEffect("tap that creature"), true, false, true);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("and it"));
        this.addAbility(ability);
    }

    private KashiTribeWarriors(final KashiTribeWarriors card) {
        super(card);
    }

    @Override
    public KashiTribeWarriors copy() {
        return new KashiTribeWarriors(this);
    }
}