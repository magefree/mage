
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MatsuTribeSniper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public MatsuTribeSniper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {t}: Matsu-Tribe Sniper deals 1 damage to target creature with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
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
