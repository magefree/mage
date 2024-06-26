package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PartyThrasher extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("noncreature spells you cast from exile");

    static {
        filter.add(new CastFromZonePredicate(Zone.EXILED));
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(new AbilityPredicate(ConvokeAbility.class))); // So there are not redundant copies being added to each card
    }

    public PartyThrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Noncreature spells you cast from exile have convoke.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter)
        ));

        // At the beginning of your precombat main phase, you may discard a card. If you do, exile the top two cards of your library, then choose one of them. You may play that card this turn.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new DoIfCostPaid(
                        new PartyThrasherEffect(),
                        new DiscardCardCost()
                ), TargetController.YOU, false
        ));
    }

    private PartyThrasher(final PartyThrasher card) {
        super(card);
    }

    @Override
    public PartyThrasher copy() {
        return new PartyThrasher(this);
    }
}

class PartyThrasherEffect extends OneShotEffect {

    PartyThrasherEffect() {
        super(Outcome.DrawCard);
        staticText = "exile the top two cards of your library, then choose one of them. You may play that card this turn";
    }

    private PartyThrasherEffect(final PartyThrasherEffect effect) {
        super(effect);
    }

    @Override
    public PartyThrasherEffect copy() {
        return new PartyThrasherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 2));
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCardsToExile(cards.getCards(game), source, game, true, null, "");
        game.processAction();
        cards.retainZone(Zone.EXILED, game);
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(Zone.EXILED, StaticFilters.FILTER_CARD_A);
        target.withNotTarget(true);
        if (!controller.choose(Outcome.DrawCard, cards, target, source, game)) {
            return true;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        String exileName = CardUtil.getSourceIdName(game, source);
        UUID exileId = CardUtil.getExileZoneId(game, source);
        ExileZone exileZone = game.getExile().createZone(exileId, exileName);
        exileZone.setCleanupOnEndTurn(true);
        game.getExile().moveToAnotherZone(card, game, exileZone);
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn)
                .setTargetPointer(new FixedTargets(cards, game)), source);
        return true;
    }

}
