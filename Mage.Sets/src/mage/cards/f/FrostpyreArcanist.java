package mage.cards.f;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrostpyreArcanist extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control a Giant or a Wizard");
    private static final FilterCard filter2 = new FilterInstantOrSorceryCard(
            "an instant or sorcery card with the same name as a card in your graveyard"
    );

    static {
        filter.add(Predicates.or(
                SubType.GIANT.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
        filter2.add(FrostpyreArcanistPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a Giant or Wizard");

    public FrostpyreArcanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast if you control a Giant or a Wizard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition)
        ).addHint(hint));

        // When Frostpyre Arcanist enters the battlefield, search your library for an instant or sorcery card with the same name as a card in your graveyard, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter2), true, true
        )));
    }

    private FrostpyreArcanist(final FrostpyreArcanist card) {
        super(card);
    }

    @Override
    public FrostpyreArcanist copy() {
        return new FrostpyreArcanist(this);
    }
}

enum FrostpyreArcanistPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Card>> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Player player = game.getPlayer(input.getPlayerId());
        if (player == null || player.getGraveyard().isEmpty()) {
            return false;
        }
        return player
                .getGraveyard()
                .getCards(game)
                .stream()
                .map(MageObject::getName)
                .anyMatch(input.getObject().getName()::equals);
    }
}