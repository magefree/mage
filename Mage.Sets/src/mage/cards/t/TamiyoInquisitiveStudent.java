package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.delayed.UntilYourNextTurnDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerLibraryCount;
import mage.abilities.dynamicvalue.common.HalfValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.TamiyoSeasonedScholarEmblem;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TamiyoInquisitiveStudent extends TransformingDoubleFacedCard {

    private static final DynamicValue xValue = new HalfValue(CardsInControllerLibraryCount.instance, true);

    public TamiyoInquisitiveStudent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MOONFOLK, SubType.WIZARD}, "{U}",
                "Tamiyo, Seasoned Scholar",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.TAMIYO}, "GU");

        this.getLeftHalfCard().setPT(0, 3);
        this.getRightHalfCard().setStartingLoyalty(2);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Tamiyo, Inquisitive Student attacks, investigate.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new InvestigateEffect()));

        // When you draw your third card in a turn, exile Tamiyo, then return her to the battlefield transformed under her owner's control.
        this.getLeftHalfCard().addAbility(new DrawNthCardTriggeredAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.SHE),
                false, 3
        ).setTriggerPhrase("When you draw your third card in a turn, "));

        // Tamiyo, Seasoned Scholar

        // +2: Until your next turn, whenever a creature attacks you or a planeswalker you control, it gets -1/-0 until end of turn.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(
                new UntilYourNextTurnDelayedTriggeredAbility(
                        new AttacksAllTriggeredAbility(
                                new BoostTargetEffect(-1, 0, Duration.EndOfTurn)
                                        .setText("it gets -1/-0 until end of turn"),
                                false, StaticFilters.FILTER_PERMANENT_CREATURE,
                                SetTargetPointer.PERMANENT, true
                        )
                )
        ), 2));

        // -3: Return target instant or sorcery card from your graveyard to your hand. If it's a green card, add one mana of any color.
        Ability ability = new LoyaltyAbility(new TamiyoSeasonedScholarMinus3Effect(), -3);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.getRightHalfCard().addAbility(ability);

        // -7: Draw cards equal to half the number of cards in your library, rounded up. You get an emblem with "You have no maximum hand size."
        ability = new LoyaltyAbility(
                new DrawCardSourceControllerEffect(xValue)
                        .setText("Draw cards equal to half the number of cards in your library, rounded up."),
                -7
        );
        ability.addEffect(new GetEmblemEffect(new TamiyoSeasonedScholarEmblem()));
        this.getRightHalfCard().addAbility(ability);
    }

    private TamiyoInquisitiveStudent(final TamiyoInquisitiveStudent card) {
        super(card);
    }

    @Override
    public TamiyoInquisitiveStudent copy() {
        return new TamiyoInquisitiveStudent(this);
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
