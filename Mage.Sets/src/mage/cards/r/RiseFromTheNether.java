package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.MindlessOneToken;

/**
 * @author muz
 */
public final class RiseFromTheNether extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("noncreature, nonland cards in your graveyard");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public RiseFromTheNether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{R}");
        this.subtype.add(SubType.ARCANE);

        // Create a 3/1 black Mindless-One creature token for each noncreature, nonland card in your graveyard.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
            new MindlessOneToken(), new CardsInControllerGraveyardCount(filter)
        ));
    }

    private RiseFromTheNether(final RiseFromTheNether card) {
        super(card);
    }

    @Override
    public RiseFromTheNether copy() {
        return new RiseFromTheNether(this);
    }
}
