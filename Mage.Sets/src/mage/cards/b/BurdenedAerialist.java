package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author weirddan455
 */
public final class BurdenedAerialist extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public BurdenedAerialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Burdened Aerialist enters the battlefield, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Whenever you sacrifice a token, Burdened Aerialist gains flying until end of turn.
        this.addAbility(new SacrificePermanentTriggeredAbility(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), filter));
    }

    private BurdenedAerialist(final BurdenedAerialist card) {
        super(card);
    }

    @Override
    public BurdenedAerialist copy() {
        return new BurdenedAerialist(this);
    }
}
