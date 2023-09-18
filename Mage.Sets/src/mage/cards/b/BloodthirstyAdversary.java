package mage.cards.b;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfAnyNumberCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class BloodthirstyAdversary extends CardImpl {

    public BloodthirstyAdversary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Bloodthirsty Adversary enters the battlefield, you may pay {2}{R} any number of times.
        // When you pay this cost one or more times, put that many +1/+1 counters on Bloodthirsty Adversary,
        // then exile up to that many target instant and/or sorcery cards with mana value 3 or less from your graveyard and copy them.
        // You may cast any number of the copies without paying their mana costs.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfAnyNumberCostPaid(
                new BloodthirstyAdversaryEffect(), new ManaCostsImpl<>("{2}{R}")
        )));
    }

    private BloodthirstyAdversary(final BloodthirstyAdversary card) {
        super(card);
    }

    @Override
    public BloodthirstyAdversary copy() {
        return new BloodthirstyAdversary(this);
    }
}

class BloodthirstyAdversaryEffect extends OneShotEffect {

    private static final FilterInstantOrSorceryCard filter =
            new FilterInstantOrSorceryCard("instant and/or sorcery cards with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public BloodthirstyAdversaryEffect() {
        super(Outcome.Benefit);
        staticText = "put that many +1/+1 counters on {this}, " +
                "then exile up to that many target instant and/or sorcery cards with mana value 3 or less from your graveyard and copy them. " +
                "You may cast any number of the copies without paying their mana costs";
    }

    private BloodthirstyAdversaryEffect(final BloodthirstyAdversaryEffect effect) {
        super(effect);
    }

    @Override
    public BloodthirstyAdversaryEffect copy() {
        return new BloodthirstyAdversaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer timesPaid = (Integer) getValue("timesPaid");
        if (timesPaid == null || timesPaid <= 0) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(timesPaid)),
                false, staticText
        );
        ability.addEffect(new BloodthirstyAdversaryCopyEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, timesPaid, filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class BloodthirstyAdversaryCopyEffect extends OneShotEffect {

    public BloodthirstyAdversaryCopyEffect() {
        super(Outcome.PlayForFree);
    }

    private BloodthirstyAdversaryCopyEffect(final BloodthirstyAdversaryCopyEffect effect) {
        super(effect);
    }

    @Override
    public BloodthirstyAdversaryCopyEffect copy() {
        return new BloodthirstyAdversaryCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToExile = new LinkedHashSet<>();
        for (UUID cardId : targetPointer.getTargets(game, source)) {
            Card card = game.getCard(cardId);
            if (card != null) {
                cardsToExile.add(card);
            }
        }
        if (cardsToExile.isEmpty()) {
            return false;
        }
        controller.moveCards(cardsToExile, Zone.EXILED, source, game);
        ApprovingObject approvingObject = new ApprovingObject(source, game);
        for (Card card : cardsToExile) {
            Card copiedCard = game.copyCard(card, source, controller.getId());
            if (copiedCard != null && controller.chooseUse(outcome, "Cast " + copiedCard.getName() + " for free?", source, game)) {
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                controller.cast(
                        controller.chooseAbilityForCast(copiedCard, game, true),
                        game, true, approvingObject
                );
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
            }
        }
        return true;
    }
}
