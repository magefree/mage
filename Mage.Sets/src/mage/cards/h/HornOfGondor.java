package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HornOfGondor extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.HUMAN, "Humans you control")
    );
    private static final Hint hint = new ValueHint("Humans you control", xValue);

    public HornOfGondor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Horn of Gondor enters the battlefield, create a 1/1 white Human Soldier creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken())));

        // {3}, {T}: Create X 1/1 white Human Soldier creature tokens, where X is the number of Humans you control.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new HumanSoldierToken(), xValue)
                        .setText("create X 1/1 white Human Soldier creature tokens, where X is the number of Humans you control"),
                new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(hint));
    }

    private HornOfGondor(final HornOfGondor card) {
        super(card);
    }

    @Override
    public HornOfGondor copy() {
        return new HornOfGondor(this);
    }
}
