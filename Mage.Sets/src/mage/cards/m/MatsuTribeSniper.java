
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MatsuTribeSniper extends CardImpl {

    public MatsuTribeSniper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {t}: Matsu-Tribe Sniper deals 1 damage to target creature with flying.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);

        // Whenever Matsu-Tribe Sniper deals damage to a creature, tap that creature and it doesn't untap during its controller's next untap step.
        ability = new DealsDamageToACreatureTriggeredAbility(new TapTargetEffect("tap that creature"), false, false, true);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("and it"));
        this.addAbility(ability);
    }

    private MatsuTribeSniper(final MatsuTribeSniper card) {
        super(card);
    }

    @Override
    public MatsuTribeSniper copy() {
        return new MatsuTribeSniper(this);
    }
}
