
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ArborColossus extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying an opponent controls");
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ArborColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}{G}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // {3}{G}{G}{G}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{3}{G}{G}{G}", 3));
        // When Arbor Colossus becomes monstrous, destroy target creature with flying an opponent controls.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new DestroyTargetEffect());
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private ArborColossus(final ArborColossus card) {
        super(card);
    }

    @Override
    public ArborColossus copy() {
        return new ArborColossus(this);
    }
}
