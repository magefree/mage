
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX
 */
public final class MatsuTribeBirdstalker extends CardImpl {

    public MatsuTribeBirdstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Kashi-Tribe Elite deals combat damage to a creature, tap that creature and it doesn't untap during its controller's next untap step.
        Ability ability;
        ability = new DealsDamageToACreatureTriggeredAbility(new TapTargetEffect("tap that creature"), true, false, true);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("and it"));
        this.addAbility(ability);

        // {G}: Matsu-Tribe Birdstalker gains reach until end of turn. (It can block creatures with flying.)
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(ReachAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{G}")));
    }

    private MatsuTribeBirdstalker(final MatsuTribeBirdstalker card) {
        super(card);
    }

    @Override
    public MatsuTribeBirdstalker copy() {
        return new MatsuTribeBirdstalker(this);
    }
}