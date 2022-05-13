
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author Plopman
 */
public final class ArchdemonOfUnx extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Zombie creature");

    static {
        filter.add(Predicates.not(SubType.ZOMBIE.getPredicate()));
    }

    public ArchdemonOfUnx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, sacrifice a non-Zombie creature, then create a 2/2 black Zombie creature token.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeControllerEffect(filter, 1, ""), TargetController.YOU, false);
        ability.addEffect(new CreateTokenEffect(new ZombieToken()).concatBy(", then"));
        this.addAbility(ability);
    }

    private ArchdemonOfUnx(final ArchdemonOfUnx card) {
        super(card);
    }

    @Override
    public ArchdemonOfUnx copy() {
        return new ArchdemonOfUnx(this);
    }
}
