
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class KrosanWarchief extends CardImpl {

    private static final FilterCard filter = new FilterCard("Beast spells");
    private static final FilterCreaturePermanent filterTarget = new FilterCreaturePermanent("Beast");

    static {
        filter.add(SubType.BEAST.getPredicate());
        filterTarget.add(SubType.BEAST.getPredicate());
    }

    public KrosanWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Beast spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
        // {1}{G}: Regenerate target Beast.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new RegenerateTargetEffect(),
                new ManaCostsImpl<>("{1}{G}"));
        Target target = new TargetCreaturePermanent(filterTarget);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private KrosanWarchief(final KrosanWarchief card) {
        super(card);
    }

    @Override
    public KrosanWarchief copy() {
        return new KrosanWarchief(this);
    }
}
