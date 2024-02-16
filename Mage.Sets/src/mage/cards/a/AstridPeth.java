package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AstridPeth extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Clue or Food");

    static {
        filter.add(Predicates.or(
                SubType.CLUE.getPredicate(),
                SubType.FOOD.getPredicate()
        ));
    }

    public AstridPeth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Astrid Peth enters the battlefield or attacks, create a Food token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Brand-new Sky -- Whenever you sacrifice a Clue or Food, Astrid Peth explores.
        this.addAbility(
                new SacrificePermanentTriggeredAbility(
                        new ExploreSourceEffect(true, "{this}"),
                        filter
                ).withFlavorWord("Brand-new Sky")
        );
    }

    private AstridPeth(final AstridPeth card) {
        super(card);
    }

    @Override
    public AstridPeth copy() {
        return new AstridPeth(this);
    }
}
