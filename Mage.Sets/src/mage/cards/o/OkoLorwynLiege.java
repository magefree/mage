package mage.cards.o;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAllCreatureTypesTargetEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.OkoShadowmoorScionEmblem;
import mage.game.permanent.token.ElkToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OkoLorwynLiege extends TransformingDoubleFacedCard {

    public OkoLorwynLiege(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.OKO}, "{2}{U}",
                "Oko, Shadowmoor Scion",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.OKO}, "G"
        );
        this.getLeftHalfCard().setStartingLoyalty(3);
        this.getRightHalfCard().setStartingLoyalty(3);

        // At the beginning of your first main phase, you may pay {G}. If you do, transform Oko.
        this.getLeftHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{G}"))
        ));

        // +2: Up to one target creature gains all creature types.
        Ability ability = new LoyaltyAbility(new GainAllCreatureTypesTargetEffect(Duration.Custom), 2);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.getLeftHalfCard().addAbility(ability);

        // +1: Target creature gets -2/-0 until your next turn.
        ability = new LoyaltyAbility(new BoostTargetEffect(-2, -0, Duration.UntilYourNextTurn), 1);
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Oko, Shadowmoor Scion
        // At the beginning of your first main phase, you may pay {U}. If you do, transform Oko.
        this.getRightHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{U}"))
        ));

        // -1: Mill three cards. You may put a permanent card from among them into your hand.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(
                new MillThenPutInHandEffect(3, StaticFilters.FILTER_CARD_PERMANENT), -1
        ));

        // -3: Create two 3/3 green Elk creature tokens.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(
                new CreateTokenEffect(new ElkToken(), 2), -3
        ));

        // -6: Choose a creature type. You get an emblem with "Creatures you control of the chosen type get +3/+3 and have vigilance and hexproof."
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new OkoShadowmoorScionEffect(), -6));
    }

    private OkoLorwynLiege(final OkoLorwynLiege card) {
        super(card);
    }

    @Override
    public OkoLorwynLiege copy() {
        return new OkoLorwynLiege(this);
    }
}

class OkoShadowmoorScionEffect extends OneShotEffect {

    OkoShadowmoorScionEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature type. You get an emblem with " +
                "\"Creatures you control of the chosen type get +3/+3 and have vigilance and hexproof.\"";
    }

    private OkoShadowmoorScionEffect(final OkoShadowmoorScionEffect effect) {
        super(effect);
    }

    @Override
    public OkoShadowmoorScionEffect copy() {
        return new OkoShadowmoorScionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || sourceObject == null) {
            return false;
        }
        Choice choice = new ChoiceCreatureType(game, source);
        player.choose(outcome, choice, game);
        SubType subType = SubType.byDescription(choice.getChoice());
        if (subType == null) {
            return false;
        }
        game.addEmblem(new OkoShadowmoorScionEmblem(subType), sourceObject, source);
        return true;
    }
}
