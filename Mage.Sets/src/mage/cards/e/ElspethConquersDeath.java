package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public final class ElspethConquersDeath extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("permanent an opponent controls with mana value 3 or greater");
    private static final FilterCard filter2
            = new FilterCard("creature or planeswalker card from your graveyard");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
        filter2.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public ElspethConquersDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I - Exile target permanent an opponent controls with converted mana cost 3 or greater.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new ExileTargetEffect(), new TargetPermanent(filter)
        );

        // II - Noncreature spells your opponents cast cost {2} more to cast until your next turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new ElspethConquersDeathCostEffect());

        // III - Return target creature or planeswalker card from your graveyard to the battlefield. Put a +1/+1 counter or a loyalty counter on it.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new ElspethConquersDeathReturnEffect(), new TargetCardInYourGraveyard(filter2)
        );
        this.addAbility(sagaAbility);
    }

    private ElspethConquersDeath(final ElspethConquersDeath card) {
        super(card);
    }

    @Override
    public ElspethConquersDeath copy() {
        return new ElspethConquersDeath(this);
    }
}

class ElspethConquersDeathCostEffect extends CostModificationEffectImpl {

    ElspethConquersDeathCostEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Noncreature spells your opponents cast cost {2} more to cast until your next turn";
    }

    private ElspethConquersDeathCostEffect(ElspethConquersDeathCostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility)
                && game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
            if (spellCard != null) {
                return !spellCard.isCreature(game);
            }
        }
        return false;
    }

    @Override
    public ElspethConquersDeathCostEffect copy() {
        return new ElspethConquersDeathCostEffect(this);
    }
}

class ElspethConquersDeathReturnEffect extends OneShotEffect {

    ElspethConquersDeathReturnEffect() {
        super(Outcome.Benefit);
        staticText = "Return target creature or planeswalker card from your graveyard to the battlefield. "
                + "Put a +1/+1 counter or a loyalty counter on it";
    }

    private ElspethConquersDeathReturnEffect(final ElspethConquersDeathReturnEffect effect) {
        super(effect);
    }

    @Override
    public ElspethConquersDeathReturnEffect copy() {
        return new ElspethConquersDeathReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        Counter counter = player.chooseUse(
                outcome, "Choose a type of counter to add",
                null, "+1/+1", "Loyalty", source, game
        ) ? CounterType.P1P1.createInstance() : CounterType.LOYALTY.createInstance();
        permanent.addCounters(counter, source.getControllerId(), source, game);
        return true;
    }

}
