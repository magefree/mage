package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarteredCow extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(BarteredCowPredicate.instance);
    }

    public BarteredCow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.OX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Bartered Cow dies or when you discard it, create a Food token.
        this.addAbility(new OrTriggeredAbility(
                Zone.ALL, new CreateTokenEffect(new FoodToken()), false,
                "When {this} dies or when you discard it, ", new DiesSourceTriggeredAbility((Effect) null),
                new DiscardCardControllerTriggeredAbility(null, false, filter)
        ));
    }

    private BarteredCow(final BarteredCow card) {
        super(card);
    }

    @Override
    public BarteredCow copy() {
        return new BarteredCow(this);
    }
}

enum BarteredCowPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return input.getObject().getId().equals(input.getSourceId());
    }
}
