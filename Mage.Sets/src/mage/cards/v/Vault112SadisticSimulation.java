package mage.cards.v;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Vault112SadisticSimulation extends CardImpl {

    public Vault112SadisticSimulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Tap up to one target creature and put a stun counter on it. You get {E}{E}.
        Effects effects = new Effects();
        effects.add(new TapTargetEffect().setText("Tap up to one target creature"));
        effects.add(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on it."));
        effects.add(new GetEnergyCountersControllerEffect(2));
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                effects, new TargetCreaturePermanent(0, 1)
        );

        // III -- Pay any amount of {E}. If you paid one or more {E} this way, shuffle your library, then exile that many cards from the top. You may play one of those cards without paying its mana cost.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new Vault112SadisticSimulationChapterEffect()
        );

        this.addAbility(sagaAbility);
    }

    private Vault112SadisticSimulation(final Vault112SadisticSimulation card) {
        super(card);
    }

    @Override
    public Vault112SadisticSimulation copy() {
        return new Vault112SadisticSimulation(this);
    }
}

class Vault112SadisticSimulationChapterEffect extends OneShotEffect {

    Vault112SadisticSimulationChapterEffect() {
        super(Outcome.Benefit);
        staticText = "Pay any amount of {E}. If you paid one or more {E} this way, shuffle your library, "
                + "then exile that many cards from the top. "
                + "You may play one of those cards without paying its mana cost.";
    }

    private Vault112SadisticSimulationChapterEffect(final Vault112SadisticSimulationChapterEffect effect) {
        super(effect);
    }

    @Override
    public Vault112SadisticSimulationChapterEffect copy() {
        return new Vault112SadisticSimulationChapterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int numberToPay = controller.getAmount(
                0, controller.getCountersCount(CounterType.ENERGY),
                "How many {E} do you like to pay?", game
        );
        if (numberToPay <= 0) {
            return true;
        }
        // Pay the chosen cost.
        Cost cost = new PayEnergyCost(numberToPay);
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return true;
        }
        // Shuffle Library
        controller.shuffleLibrary(source, game);
        // Exile top X cards.
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, numberToPay));
        controller.moveCardsToExile(cards.getCards(game), source, game, true, null, "");
        cards.retainZone(Zone.EXILED, game);
        if (cards.isEmpty()) {
            return true;
        }
        // Choose one, you may cast it without paying its mana cost.
        TargetCardInExile target = new TargetCardInExile(0, 1, StaticFilters.FILTER_CARD_CARDS);
        controller.choose(Outcome.PlayForFree, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        // Since we don't know if the card can be played as a land or cast, we search for both and activate the ability.
        ActivatedAbility ability = controller.chooseLandOrSpellAbility(card, game, true);
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        if (ability instanceof SpellAbility) {
            controller.cast((SpellAbility) ability, game, true, new ApprovingObject(source, game));
        } else if (ability instanceof PlayLandAbility && controller.canPlayLand()) {
            controller.playLand(card, game, true);
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        return true;
    }

}