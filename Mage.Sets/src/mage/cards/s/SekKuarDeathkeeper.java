
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SekKuarDeathkeeperGravebornToken;

/**
 *
 * @author LevelX2
 */
public final class SekKuarDeathkeeper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public SekKuarDeathkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever another nontoken creature you control dies, create a 3/1 black and red Graveborn creature token with haste.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new SekKuarDeathkeeperGravebornToken()), false, filter));
    }

    public SekKuarDeathkeeper(final SekKuarDeathkeeper card) {
        super(card);
    }

    @Override
    public SekKuarDeathkeeper copy() {
        return new SekKuarDeathkeeper(this);
    }
}
