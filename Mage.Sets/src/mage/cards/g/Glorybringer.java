
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ExertAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Glorybringer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Dragon creature an opponent controls");

    static {
        filter.add(Predicates.not(SubType.DRAGON.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Glorybringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // You may exert Glorybringer as it attacks. When you do, it deals 4 damage to target non-Dragon creature an opponent controls.
        Effect effect = new DamageTargetEffect(4);
        effect.setText("it deals 4 damage to target non-Dragon creature an opponent controls");
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(effect);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(new ExertAbility(ability));
    }

    private Glorybringer(final Glorybringer card) {
        super(card);
    }

    @Override
    public Glorybringer copy() {
        return new Glorybringer(this);
    }
}
