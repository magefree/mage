package mage.cards.n;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.ContinuousEffectImpl;
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
import mage.filter.common.FilterBasicCard;
import mage.filter.common.FilterLandPermanent;
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

    private static final FilterCard filter = new FilterBasicCard(SubType.FOREST);

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterLandPermanent("you control seven or more lands"),
            ComparisonType.MORE_THAN, 6, true
    );

    public NissaVastwoodSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELF, SubType.SCOUT}, "{2}{G}",
                "Nissa, Sage Animist",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.NISSA}, "G"
        );

        // Nissa, Vastwood Seer
        this.getLeftHalfCard().setPT(2, 2);

        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true
        ));

        // Whenever a land you control enters, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.
        this.getLeftHalfCard().addAbility(new LandfallAbility(new ExileAndReturnSourceEffect(
                PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.SHE
        )).withInterveningIf(condition).setAbilityWord(null));

        // Nissa, Sage Animist
        this.getRightHalfCard().setStartingLoyalty(3);

        // +1: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new NissaSageAnimistPlusOneEffect(), 1));

        // -2: Create a legendary 4/4 green Elemental creature token named Ashaya, the Awoken World.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new CreateTokenEffect(new NissaSageAnimistToken()), -2));

        // -7: Untap up to six target lands. They become 6/6 Elemental creatures. They're still lands.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), -7);
        ability.addTarget(new TargetLandPermanent(0, 6));
        ability.addEffect(new NissaSageAnimistMinusAnimateEffect());
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

    private NissaSageAnimistPlusOneEffect(final NissaSageAnimistPlusOneEffect effect) {
        super(effect);
    }

    @Override
    public NissaSageAnimistPlusOneEffect copy() {
        return new NissaSageAnimistPlusOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
            Zone targetZone = Zone.HAND;
            if (card.isLand(game)) {
                targetZone = Zone.BATTLEFIELD;
            }
            return controller.moveCards(card, targetZone, source, game);
        }
        return true;
    }
}

class NissaSageAnimistMinusAnimateEffect extends OneShotEffect {

    NissaSageAnimistMinusAnimateEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "They become 6/6 Elemental creatures. They're still lands";
    }

    private NissaSageAnimistMinusAnimateEffect(final NissaSageAnimistMinusAnimateEffect effect) {
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
            if (permanent != null) {
                ContinuousEffectImpl effect = new BecomesCreatureTargetEffect(new CreatureToken(6, 6, "", SubType.ELEMENTAL), false, true, Duration.Custom);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
