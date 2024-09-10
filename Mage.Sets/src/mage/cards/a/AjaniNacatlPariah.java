package mage.cards.a;

import mage.MageInt;
import mage.abilities.Pronoun;
import mage.abilities.common.DiesOneOrMoreCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.CatWarrior21Token;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AjaniNacatlPariah extends CardImpl {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.CAT, "other Cats you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AjaniNacatlPariah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.a.AjaniNacatlAvenger.class;

        // When Ajani, Nacatl Pariah enters the battlefield, create a 2/1 white Cat Warrior creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new CatWarrior21Token())));

        // Whenever one or more other Cats you control die, you may exile Ajani, then return him to the battlefield transformed under his owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesOneOrMoreCreatureTriggeredAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.HE),
                filter,
                true));
    }

    private AjaniNacatlPariah(final AjaniNacatlPariah card) {
        super(card);
    }

    @Override
    public AjaniNacatlPariah copy() {
        return new AjaniNacatlPariah(this);
    }
}
