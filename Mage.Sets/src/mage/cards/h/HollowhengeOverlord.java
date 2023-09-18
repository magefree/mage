package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HollowhengeOverlord extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint(
            "Wolves and Werewolves you control", xValue
    );

    public HollowhengeOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // At the beginning of your upkeep, for each creature you control that's a Wolf or a Werewolf, create a 2/2 green Wolf creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new WolfToken(), xValue)
                        .setText("for each creature you control that's a Wolf or a Werewolf, " +
                                "create a 2/2 green Wolf creature token"),
                TargetController.YOU, false
        ).addHint(hint));
    }

    private HollowhengeOverlord(final HollowhengeOverlord card) {
        super(card);
    }

    @Override
    public HollowhengeOverlord copy() {
        return new HollowhengeOverlord(this);
    }
}
