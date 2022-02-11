package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshiokSculptorOfFears extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card from a graveyard");

    public AshiokSculptorOfFears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASHIOK);
        this.setStartingLoyalty(4);

        // +2: Draw a card. Each player puts the top two cards of their library into their graveyard.
        Ability ability = new LoyaltyAbility(
                new DrawCardSourceControllerEffect(1).setText("draw a card"), 2
        );
        ability.addEffect(new MillCardsEachPlayerEffect(2, TargetController.ANY));
        this.addAbility(ability);

        // −5: Put target creature card from a graveyard onto the battlefield under your control.
        ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("put target creature card from a graveyard onto the battlefield under your control"), -5);
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

        // −11: Gain control of all creatures target opponent controls.
        ability = new LoyaltyAbility(new AshiokSculptorOfFearsEffect(), -11);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private AshiokSculptorOfFears(final AshiokSculptorOfFears card) {
        super(card);
    }

    @Override
    public AshiokSculptorOfFears copy() {
        return new AshiokSculptorOfFears(this);
    }
}

class AshiokSculptorOfFearsEffect extends OneShotEffect {

    AshiokSculptorOfFearsEffect() {
        super(Outcome.Benefit);
        staticText = "gain control of all creatures target opponent controls";
    }

    private AshiokSculptorOfFearsEffect(final AshiokSculptorOfFearsEffect effect) {
        super(effect);
    }

    @Override
    public AshiokSculptorOfFearsEffect copy() {
        return new AshiokSculptorOfFearsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(source.getFirstTarget()));

        new GainControlAllEffect(Duration.Custom, filter).apply(game, source);
        return true;
    }
}
