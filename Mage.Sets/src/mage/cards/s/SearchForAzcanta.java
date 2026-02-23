package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SearchForAzcanta extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("a noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public SearchForAzcanta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{1}{U}",
                "Azcanta, The Sunken Ruin",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, "U"
        );

        // Search for Azcanta
        // At the beginning of your upkeep, look at the top card of your library. You may put it into your graveyard. Then if you have seven or more cards in your graveyard, you may transform Search for Azcanta.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new SurveilEffect(1, false)
        );
        ability.addEffect(new SearchForAzcantaEffect());
        this.getLeftHalfCard().addAbility(ability);

        // Azcanta, The Sunken Ruin
        // {T} : Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());

        // {2}{U} , {T} : Look at the top four cards of your library. You may reveal a noncreature, nonland card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        Ability ability2 = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(4, 1, filter, PutCards.HAND, PutCards.BOTTOM_ANY),
                new ManaCostsImpl<>("{2}{U}")
        );
        ability2.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability2);
    }

    private SearchForAzcanta(final SearchForAzcanta card) {
        super(card);
    }

    @Override
    public SearchForAzcanta copy() {
        return new SearchForAzcanta(this);
    }
}

class SearchForAzcantaEffect extends OneShotEffect {

    SearchForAzcantaEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Then if you have seven or more cards in your graveyard, you may transform {this}.";
    }

    private SearchForAzcantaEffect(final SearchForAzcantaEffect effect) {
        super(effect);
    }

    @Override
    public SearchForAzcantaEffect copy() {
        return new SearchForAzcantaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return controller != null
                && permanent != null
                && controller.getGraveyard().size() >= 7
                && controller.chooseUse(outcome, "Transform " + permanent.getName() + '?', source, game)
                && permanent.transform(source, game);
    }
}
