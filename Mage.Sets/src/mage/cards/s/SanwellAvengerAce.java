package mage.cards.s;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanwellAvengerAce extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactCreaturePermanent();

    static {
        filter.add(AttackingPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public SanwellAvengerAce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // As long as an artifact creature you control is attacking, prevent all damage that would be dealt to Sanwell, Avenger Ace.
        this.addAbility(new SimpleStaticAbility(new ConditionalPreventionEffect(
                new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield), condition, "as long as " +
                "an artifact creature you control is attacking, prevent all damage that would be dealt to {this}"
        )));

        // Whenever Sanwell becomes tapped, exile the top six cards of your library. You may cast a Vehicle or artifact creature spell from among them. Then put the rest on the bottom of your library in a random order.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new SanwellAvengerAceEffect()));
    }

    private SanwellAvengerAce(final SanwellAvengerAce card) {
        super(card);
    }

    @Override
    public SanwellAvengerAce copy() {
        return new SanwellAvengerAce(this);
    }
}

class SanwellAvengerAceEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterArtifactCard();

    static {
        filter.add(Predicates.or(
                SubType.VEHICLE.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    SanwellAvengerAceEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top six cards of your library. You may cast a Vehicle or artifact creature spell " +
                "from among them. Then put the rest on the bottom of your library in a random order";
    }

    private SanwellAvengerAceEffect(final SanwellAvengerAceEffect effect) {
        super(effect);
    }

    @Override
    public SanwellAvengerAceEffect copy() {
        return new SanwellAvengerAceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        player.moveCards(cards, Zone.EXILED, source, game);
        castSpell(cards, player, source, game);
        cards.retainZone(Zone.EXILED, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }

    // Based on CardUtil.castSpellWithAttributesForFree
    // also see Anrakyr the Traveller
    // this sort of thing probably deserves refactoring
    private static void castSpell(Cards cards, Player player, Ability source, Game game) {
        Map<UUID, List<Card>> cardMap = new HashMap<>();
        for (Card card : cards.getCards(game)) {
            List<Card> castableComponents = CardUtil.getCastableComponents(card, filter, source, player, game, null, false);
            if (!castableComponents.isEmpty()) {
                cardMap.put(card.getId(), castableComponents);
            }
        }
        if (cardMap.isEmpty()) {
            return;
        }
        Cards castableCards = new CardsImpl(cardMap.keySet());
        TargetCard target = new TargetCard(0, 1, Zone.ALL, filter);
        target.withNotTarget(true);
        target.withChooseHint("to cast");
        player.choose(Outcome.Benefit, castableCards, target, source, game);
        Card cardToCast = castableCards.get(target.getFirstTarget(), game);
        if (cardToCast == null) {
            return;
        }
        List<Card> partsToCast = cardMap.get(cardToCast.getId());
        if (partsToCast.isEmpty()) {
            return;
        }
        partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE));
        SpellAbility chosenAbility = player.chooseAbilityForCast(cardToCast, game, false);
        if (chosenAbility != null) {
            player.cast(chosenAbility, game, false, new ApprovingObject(source, game));
        }
        partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null));
    }

}
