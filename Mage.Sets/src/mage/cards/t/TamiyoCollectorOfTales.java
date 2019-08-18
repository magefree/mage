package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.*;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class TamiyoCollectorOfTales extends CardImpl {

    public TamiyoCollectorOfTales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TAMIYO);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // Spells and abilities your opponents control can't cause you to discard cards or sacrifice permanents.
        this.addAbility(new SimpleStaticAbility(new TamiyoCollectorOfTalesRuleEffect()));

        // +1: Choose a nonland card name, then reveal the top four cards of your library. Put all cards with the chosen name from among them into your hand and the rest into your graveyard.
        this.addAbility(new LoyaltyAbility(new TamiyoCollectorOfTalesEffect(), 1));

        // -3: Return target card from your graveyard to your hand.
        Ability ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -3);
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    private TamiyoCollectorOfTales(final TamiyoCollectorOfTales card) {
        super(card);
    }

    @Override
    public TamiyoCollectorOfTales copy() {
        return new TamiyoCollectorOfTales(this);
    }
}

class TamiyoCollectorOfTalesRuleEffect extends ContinuousRuleModifyingEffectImpl {

    TamiyoCollectorOfTalesRuleEffect() {
        super(Duration.WhileOnBattlefield, Benefit);
        staticText = "Spells and abilities your opponents control can't " +
                "cause you to discard cards or sacrifice permanents";
    }

    private TamiyoCollectorOfTalesRuleEffect(final TamiyoCollectorOfTalesRuleEffect effect) {
        super(effect);
    }

    @Override
    public TamiyoCollectorOfTalesRuleEffect copy() {
        return new TamiyoCollectorOfTalesRuleEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICE_PERMANENT
                || event.getType() == GameEvent.EventType.DISCARD_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object instanceof PermanentCard) {
                if (game.getOpponents(source.getControllerId()).contains(((PermanentCard) object).getControllerId())) {
                    return true;
                }
            }
            if (object instanceof Spell) {
                if (game.getOpponents(source.getControllerId()).contains(((Spell) object).getControllerId())) {
                    return true;
                }
            }
            if (object instanceof Card) {
                if (game.getOpponents(source.getControllerId()).contains(((Card) object).getOwnerId())) {
                    return true;
                }
            }
            if (object instanceof StackAbility) {
                if (game.getOpponents(source.getControllerId()).contains(((StackAbility) object).getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }
}

class TamiyoCollectorOfTalesEffect extends OneShotEffect {

    TamiyoCollectorOfTalesEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a nonland card name, then reveal the top four cards of your library. " +
                "Put all cards with the chosen name from among them into your hand and the rest into your graveyard.";
    }

    private TamiyoCollectorOfTalesEffect(final TamiyoCollectorOfTalesEffect effect) {
        super(effect);
    }

    @Override
    public TamiyoCollectorOfTalesEffect copy() {
        return new TamiyoCollectorOfTalesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Choice choice = new ChoiceImpl();
        choice.setChoices(CardRepository.instance.getNonLandNames());
        choice.setMessage("Choose a nonland card name");
        if (!player.choose(outcome, choice, game)) {
            return false;
        }
        game.informPlayers(source.getSourceObject(game).getLogName() + ", chosen name: [" + choice.getChoice() + ']');
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        Cards cards2 = new CardsImpl();
        player.revealCards(source, cards, game);
        for (Card card : cards.getCards(game)) {
            if(card.isSplitCard()){
                if(((SplitCard) card).getLeftHalfCard().getName().equals(choice.getChoice()) ||
                        ((SplitCard) card).getRightHalfCard().getName().equals(choice.getChoice())){
                    cards2.add(card);
                }
            }
            if (card.getName().equals(choice.getChoice())) {
                cards2.add(card);
            }
        }
        cards.removeAll(cards2);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        player.moveCards(cards2, Zone.HAND, source, game);
        return true;
    }
}