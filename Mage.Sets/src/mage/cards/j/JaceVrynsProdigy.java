package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.command.emblems.JaceTelepathUnboundEmblem;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JaceVrynsProdigy extends TransformingDoubleFacedCard {

    private static final Condition condition = new CardsInControllerGraveyardCondition(5);

    public JaceVrynsProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{1}{U}",
                "Jace, Telepath Unbound",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.JACE}, "U");

        // Jace, Vryn's Prodigy (front)
        this.getLeftHalfCard().setPT(0, 2);

        // {T}: Draw a card, then discard a card. If there are five or more cards in your graveyard, exile Jace, Vryn's Prodigy, then return him to the battlefield transformed under his owner's control.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new TapSourceCost()
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.HE), condition
        ));
        this.getLeftHalfCard().addAbility(ability);

        // Jace, Telepath Unbound (back)
        this.getRightHalfCard().setStartingLoyalty(5);

        // +1: Up to one target creature gets -2/-0 until your next turn.
        Ability pwAbility = new LoyaltyAbility(new BoostTargetEffect(
                -2, 0, Duration.UntilYourNextTurn
        ).setText("Up to one target creature gets -2/-0 until your next turn"), 1);
        pwAbility.addTarget(new TargetCreaturePermanent(0, 1));
        this.getRightHalfCard().addAbility(pwAbility);

        // -3: You may cast target instant or sorcery card from your graveyard this turn. If that card would be put into your graveyard this turn, exile it instead.
        pwAbility = new LoyaltyAbility(new MayCastTargetCardEffect(Duration.EndOfTurn, true), -3);
        pwAbility.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.getRightHalfCard().addAbility(pwAbility);

        // −9: You get an emblem with "Whenever you cast a spell, target opponent mills five cards." (emblem class unchanged)
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new GetEmblemEffect(new JaceTelepathUnboundEmblem()), -9));
    }

    private JaceVrynsProdigy(final JaceVrynsProdigy card) {
        super(card);
    }

    @Override
    public JaceVrynsProdigy copy() {
        return new JaceVrynsProdigy(this);
    }
}
