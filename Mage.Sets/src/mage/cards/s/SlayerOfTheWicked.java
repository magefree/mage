
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class SlayerOfTheWicked extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vampire, Werewolf, or Zombie");

    static {
        filter.add(Predicates.or(
                SubType.VAMPIRE.getPredicate(),
                SubType.WEREWOLF.getPredicate(),
                SubType.ZOMBIE.getPredicate()));
    }

    public SlayerOfTheWicked(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Slayer of the Wicked enters the battlefield, you may destroy target Vampire, Werewolf, or Zombie.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private SlayerOfTheWicked(final SlayerOfTheWicked card) {
        super(card);
    }

    @Override
    public SlayerOfTheWicked copy() {
        return new SlayerOfTheWicked(this);
    }
}
