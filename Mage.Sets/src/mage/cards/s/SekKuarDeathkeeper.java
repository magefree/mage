
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
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SekKuarDeathkeeperGravebornToken;

/**
 *
 * @author LevelX2
 */
public final class SekKuarDeathkeeper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SekKuarDeathkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever another nontoken creature you control dies, create a 3/1 black and red Graveborn creature token with haste.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new SekKuarDeathkeeperGravebornToken()), false, filter));
    }

    private SekKuarDeathkeeper(final SekKuarDeathkeeper card) {
        super(card);
    }

    @Override
    public SekKuarDeathkeeper copy() {
        return new SekKuarDeathkeeper(this);
    }
}
