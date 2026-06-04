package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SquirrelToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheUnbeatableSquirrelGirl extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
        new FilterControlledPermanent(SubType.SQUIRREL, "Squirrels you control"), null
    );
    private static final Hint hint = new ValueHint("Squirrels you control", xValue);

    public TheUnbeatableSquirrelGirl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Do You Like Squirrels? -- Whenever The Unbeatable Squirrel Girl enters or attacks, create a 1/1 green Squirrel creature token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new CreateTokenEffect(new SquirrelToken())
        ).withFlavorWord("Do You Like Squirrels?"));

        // I LOVE Squirrels! -- {1}{G}{G}{G}: Create X 1/1 green Squirrel creature tokens, where X is the number of Squirrels you control.
        Ability ability =new SimpleActivatedAbility(
            new CreateTokenEffect(new SquirrelToken(), xValue), new ManaCostsImpl<>("{1}{G}{G}{G}")
        );
        this.addAbility(ability.addHint(hint).withFlavorWord("I LOVE Squirrels!"));
    }

    private TheUnbeatableSquirrelGirl(final TheUnbeatableSquirrelGirl card) {
        super(card);
    }

    @Override
    public TheUnbeatableSquirrelGirl copy() {
        return new TheUnbeatableSquirrelGirl(this);
    }
}
