
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KeepsakeGorgon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Gorgon creature an opponent controls");
    static {
        filter.add(Predicates.not(SubType.GORGON.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public KeepsakeGorgon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.GORGON);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // {5}{B}{B}: Monstrosity 1.
        this.addAbility(new MonstrosityAbility("{5}{B}{B}", 1));
        // When Keepsake Gorgon becomes monstrous, destroy target non-Gorgon creature an opponent controls.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new DestroyTargetEffect());
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private KeepsakeGorgon(final KeepsakeGorgon card) {
        super(card);
    }

    @Override
    public KeepsakeGorgon copy() {
        return new KeepsakeGorgon(this);
    }
}
