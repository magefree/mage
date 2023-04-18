package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutOntoBattlefieldEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfIkoria extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("non-Human creature card with mana value X or less");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
        filter.add(InvasionOfIkoriaPredicate.instance);
    }

    public InvasionOfIkoria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{X}{G}{G}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(6);
        this.secondSideCardClazz = mage.cards.z.ZilorthaApexOfIkoria.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Ikoria enters the battlefield, search your library and/or graveyard for a non-Human creature card with mana value X or less and put it onto the battlefield. If you search your library this way, shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutOntoBattlefieldEffect(filter)));
    }

    private InvasionOfIkoria(final InvasionOfIkoria card) {
        super(card);
    }

    @Override
    public InvasionOfIkoria copy() {
        return new InvasionOfIkoria(this);
    }
}

enum InvasionOfIkoriaPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue()
                <= ManacostVariableValue.ETB.calculate(game, input.getSource(), null);
    }
}