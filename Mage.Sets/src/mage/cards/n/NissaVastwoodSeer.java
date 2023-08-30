
package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.Pronoun;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.NissaSageAnimistToken;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class NissaVastwoodSeer extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("basic Forest card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.FOREST.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_LAND, ComparisonType.MORE_THAN, 6, true
    );

    public NissaVastwoodSeer(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELF, SubType.SCOUT}, "{2}{G}",
                "Nissa, Sage Animist",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.NISSA}, "G"
        );
        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setStartingLoyalty(3);

        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true));

        // Whenever a land enters the battlefield under your control, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.
        this.getLeftHalfCard().addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(
                        new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.SHE), StaticFilters.FILTER_LAND
                ), condition, "Whenever a land enters the battlefield under your control, if you control seven " +
                "or more lands, exile {this}, then return her to the battlefield transformed under her owner's control."
        ));

        // +1: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new NissaSageAnimistPlusOneEffect(), 1));

        // -2: Create a legendary 4/4 green Elemental creature token named Ashaya, the Awoken World.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new CreateTokenEffect(new NissaSageAnimistToken()), -2));

        // -7: Untap up to six target lands. They become 6/6 Elemental creatures. They're still lands.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), -7);
        ability.addEffect(new NissaSageAnimistMinusAnimateEffect());
        ability.addTarget(new TargetLandPermanent(0, 6));
        this.getRightHalfCard().addAbility(ability);
    }

    private NissaVastwoodSeer(final NissaVastwoodSeer card) {
        super(card);
    }

    @Override
    public NissaVastwoodSeer copy() {
        return new NissaVastwoodSeer(this);
    }
}

class NissaSageAnimistPlusOneEffect extends OneShotEffect {

    NissaSageAnimistPlusOneEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.";
    }

    NissaSageAnimistPlusOneEffect(final NissaSageAnimistPlusOneEffect effect) {
        super(effect);
    }

    @Override
    public NissaSageAnimistPlusOneEffect copy() {
        return new NissaSageAnimistPlusOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        return controller.moveCards(card, card.isLand(game) ? Zone.BATTLEFIELD : Zone.HAND, source, game);
    }
}

class NissaSageAnimistMinusAnimateEffect extends OneShotEffect {

    public NissaSageAnimistMinusAnimateEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "They become 6/6 Elemental creatures. They're still lands";
    }

    public NissaSageAnimistMinusAnimateEffect(final NissaSageAnimistMinusAnimateEffect effect) {
        super(effect);
    }

    @Override
    public NissaSageAnimistMinusAnimateEffect copy() {
        return new NissaSageAnimistMinusAnimateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent == null) {
                continue;
            }
            game.addEffect(new BecomesCreatureTargetEffect(
                    new CreatureToken(6, 6, "", SubType.ELEMENTAL),
                    false, true, Duration.Custom
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
