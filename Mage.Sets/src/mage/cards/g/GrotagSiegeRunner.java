
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DefenderAbility;
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
 * @author North
 */
public final class GrotagSiegeRunner extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public GrotagSiegeRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {R}, Sacrifice Grotag Siege-Runner: Destroy target creature with defender. Grotag Siege-Runner deals 2 damage to that creature's controller.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeSourceCost());
        Effect effect = new DamageTargetControllerEffect(2);
        effect.setText("{this} deals 2 damage to that creature's controller");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private GrotagSiegeRunner(final GrotagSiegeRunner card) {
        super(card);
    }

    @Override
    public GrotagSiegeRunner copy() {
        return new GrotagSiegeRunner(this);
    }
}
