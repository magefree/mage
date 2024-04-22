package mage.cards.l;

import mage.MageInt;
import mage.abilities.Pronoun;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.l.LilianaDefiantNecromancer.class;
        this.addAbility(new TransformAbility());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, create a 2/2 black Zombie creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(new ExileAndReturnSourceEffect(
                PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.SHE, false, new CreateTokenEffect(new ZombieToken())
        ), false, filter));
    }

    private LilianaHereticalHealer(final LilianaHereticalHealer card) {
        super(card);
    }

    @Override
    public LilianaHereticalHealer copy() {
        return new LilianaHereticalHealer(this);
    }
}
