package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Technomancer extends CardImpl {

    public Technomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.NECRON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // When Technomancer enters the battlefield, mill three cards, then return any number of artifact creature cards with total mana value 6 or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new TechnomancerEffect());
        this.addAbility(ability);
    }

    private Technomancer(final Technomancer card) {
        super(card);
    }

    @Override
    public Technomancer copy() {
        return new Technomancer(this);
    }
}

class TechnomancerEffect extends OneShotEffect {

    TechnomancerEffect() {
        super(Outcome.Benefit);
        staticText = ", then return any number of artifact creature cards " +
                "with total mana value 6 or less from your graveyard to the battlefield";
    }

    private TechnomancerEffect(final TechnomancerEffect effect) {
        super(effect);
    }

    @Override
    public TechnomancerEffect copy() {
        return new TechnomancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TechnomancerTarget();
        player.choose(outcome, player.getGraveyard(), target, game);
        return player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
    }
}

class TechnomancerTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter = new FilterCreatureCard(
            "artifact creature cards with total mana value 6 or less from your graveyard"
    );

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 7));
    }

    TechnomancerTarget() {
        super(0, Integer.MAX_VALUE, filter, true);
    }

    private TechnomancerTarget(final TechnomancerTarget target) {
        super(target);
    }

    @Override
    public TechnomancerTarget copy() {
        return new TechnomancerTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null &&
                this.getTargets()
                        .stream()
                        .map(game::getCard)
                        .mapToInt(Card::getManaValue)
                        .sum() + card.getManaValue() <= 6;
    }
}
