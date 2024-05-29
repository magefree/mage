package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.delayed.UntilYourNextTurnDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerLibraryCount;
import mage.abilities.dynamicvalue.common.HalfValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.TamiyoSeasonedScholarEmblem;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TamiyoSeasonedScholar extends CardImpl {

    private static final DynamicValue xValue = new HalfValue(CardsInControllerLibraryCount.instance, true);

    public TamiyoSeasonedScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TAMIYO);
        this.setStartingLoyalty(2);

        this.color.setGreen(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // +2: Until your next turn, whenever a creature attacks you or a planeswalker you control, it gets -1/-0 until end of turn.
        this.addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(
                new UntilYourNextTurnDelayedTriggeredAbility(
                        new AttacksAllTriggeredAbility(
                                new BoostTargetEffect(-1, 0, Duration.EndOfTurn)
                                        .setText("it gets -1/-0 until end of turn"),
                                false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE,
                                SetTargetPointer.PERMANENT, true
                        )
                )
        ), 2));

        // -3: Return target instant or sorcery card from your graveyard to your hand. If it's a green card, add one mana of any color.
        Ability ability = new LoyaltyAbility(new TamiyoSeasonedScholarMinus3Effect(), -3);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // -7: Draw cards equal to half the number of cards in your library, rounded up. You get an emblem with "You have no maximum hand size."
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(xValue), -7);
        ability.addEffect(new GetEmblemEffect(new TamiyoSeasonedScholarEmblem()));
        this.addAbility(ability);
    }

    private TamiyoSeasonedScholar(final TamiyoSeasonedScholar card) {
        super(card);
    }

    @Override
    public TamiyoSeasonedScholar copy() {
        return new TamiyoSeasonedScholar(this);
    }
}

class TamiyoSeasonedScholarMinus3Effect extends OneShotEffect {

    TamiyoSeasonedScholarMinus3Effect() {
        super(Outcome.DrawCard);
        this.staticText = "Return target instant or sorcery card from your graveyard to your hand. "
                + "If it's a green card, add one mana of any color";
    }

    private TamiyoSeasonedScholarMinus3Effect(final TamiyoSeasonedScholarMinus3Effect effect) {
        super(effect);
    }

    @Override
    public TamiyoSeasonedScholarMinus3Effect copy() {
        return new TamiyoSeasonedScholarMinus3Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card == null) {
            return false;
        }
        Effect effect = new ReturnToHandTargetEffect();
        effect.setTargetPointer(getTargetPointer().copy());
        effect.apply(game, source);
        if (card.getColor(game).isGreen()) {
            new AddManaOfAnyColorEffect().apply(game, source);
        }
        return true;
    }
}