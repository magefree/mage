package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeadlessRider extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ZOMBIE, "nontoken Zombie you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public HeadlessRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Wheenver Headless Rider or another nontoken Zombie you control dies, create a 2/2 black Zombie creature token.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new CreateTokenEffect(new ZombieToken()), false, filter
        ));
    }

    private HeadlessRider(final HeadlessRider card) {
        super(card);
    }

    @Override
    public HeadlessRider copy() {
        return new HeadlessRider(this);
    }
}
