
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Gender;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAndReturnTransformedSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author LevelX2
 */
public final class LilianaHereticalHealer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public LilianaHereticalHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = LilianaDefiantNecromancer.class;
        this.addAbility(new TransformAbility());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, create a 2/2 black Zombie creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(new ExileAndReturnTransformedSourceEffect(Gender.FEMALE,
                new CreateTokenEffect(new ZombieToken())), false, filter));
    }

    private LilianaHereticalHealer(final LilianaHereticalHealer card) {
        super(card);
    }

    @Override
    public LilianaHereticalHealer copy() {
        return new LilianaHereticalHealer(this);
    }
}
