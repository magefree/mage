package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.Pest11GainLifeToken;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PestilentCauldron extends ModalDoubleFacedCard {

    private static final FilterCard filter
            = new FilterCard("creature, land, and/or planeswalker cards from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public PestilentCauldron(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}{B}",
                "Restorative Burst",
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{3}{G}{G}"
        );

        // 1.
        // Pestilent Cauldron
        // Artifact
        // {T}, Discard a card: Create a 1/1 black and green Pest creature token with "When this creature dies, you gain 1 life."
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new Pest11GainLifeToken()), new TapSourceCost()
        );
        ability.addCost(new DiscardCardCost());
        this.getLeftHalfCard().addAbility(ability);

        // {1}, {T}: Each opponent mills cards equal to the amount of life you gained this turn.
        ability = new SimpleActivatedAbility(new MillCardsEachPlayerEffect(
                ControllerGainedLifeCount.instance, TargetController.OPPONENT
        ).setText("each opponent mills cards equal to the amount of life you gained this turn"), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addWatcher(new PlayerGainedLifeWatcher());
        this.getLeftHalfCard().addAbility(ability.addHint(ControllerGainedLifeCount.getHint()));

        // {4}, {T}: Exile four target cards from a single graveyard. Draw a card.
        ability = new SimpleActivatedAbility(new ExileTargetEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(
                4, 4, StaticFilters.FILTER_CARD_CARDS
        ));
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // Restorative Burst
        // Sorcery
        // Return up to two target creature, land, and/or planeswalker cards from your graveyard to your hand. Each player gains 4 life. Exile Restorative Burst.
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getRightHalfCard().getSpellAbility().addEffect(new RestorativeBurstEffect());
        this.getRightHalfCard().getSpellAbility().addEffect(new ExileSpellEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, filter));
    }

    private PestilentCauldron(final PestilentCauldron card) {
        super(card);
    }

    @Override
    public PestilentCauldron copy() {
        return new PestilentCauldron(this);
    }
}

class RestorativeBurstEffect extends OneShotEffect {

    RestorativeBurstEffect() {
        super(Outcome.GainLife);
        staticText = "Each player gains 4 life.";
    }

    private RestorativeBurstEffect(final RestorativeBurstEffect effect) {
        super(effect);
    }

    @Override
    public RestorativeBurstEffect copy() {
        return new RestorativeBurstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.gainLife(4, game, source);
            }
        }
        return true;
    }
}
