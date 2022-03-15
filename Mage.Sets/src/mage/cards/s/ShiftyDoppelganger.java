package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShiftyDoppelganger extends CardImpl {

    public ShiftyDoppelganger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{U}, Exile Shifty Doppelganger: You may put a creature card from your hand onto the battlefield. If you do, that creature gains haste until end of turn. At the beginning of the next end step, sacrifice that creature. If you do, return Shifty Doppelganger to the battlefield.
        Ability ability = new SimpleActivatedAbility(new ShiftyDoppelgangerExileEffect(), new ManaCostsImpl<>("{3}{U}"));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private ShiftyDoppelganger(final ShiftyDoppelganger card) {
        super(card);
    }

    @Override
    public ShiftyDoppelganger copy() {
        return new ShiftyDoppelganger(this);
    }
}

class ShiftyDoppelgangerExileEffect extends OneShotEffect {

    public ShiftyDoppelgangerExileEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put a creature card from your hand onto the battlefield. If you do, " +
                "that creature gains haste until end of turn. At the beginning of the next end step, " +
                "sacrifice that creature. If you do, return {this} to the battlefield";
    }

    private ShiftyDoppelgangerExileEffect(final ShiftyDoppelgangerExileEffect effect) {
        super(effect);
    }

    @Override
    public ShiftyDoppelgangerExileEffect copy() {
        return new ShiftyDoppelgangerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null
                || player.getHand().count(StaticFilters.FILTER_CARD_CREATURE, game) < 1
                || !player.chooseUse(
                Outcome.PutCardInPlay, "Put a creature card " +
                        "from your hand onto the battlefield?", source, game
        )) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE);
        player.choose(Outcome.PutCreatureInPlay, player.getHand(), target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent creature = game.getPermanent(card.getId());
        if (creature == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(creature, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ShiftyDoppelgangerReturnEffect(creature, source, game)
        ), source);
        return true;
    }
}

class ShiftyDoppelgangerReturnEffect extends OneShotEffect {

    private final MageObjectReference creatureMor;
    private final MageObjectReference sourceMor;

    ShiftyDoppelgangerReturnEffect(Permanent creature, Ability source, Game game) {
        super(Outcome.Benefit);
        this.staticText = "sacrifice that creature. If you do, return {this} to the battlefield";
        this.creatureMor = new MageObjectReference(creature, game);
        this.sourceMor = new MageObjectReference(source, 1);
    }

    private ShiftyDoppelgangerReturnEffect(final ShiftyDoppelgangerReturnEffect effect) {
        super(effect);
        this.creatureMor = effect.creatureMor;
        this.sourceMor = effect.sourceMor;
    }

    @Override
    public ShiftyDoppelgangerReturnEffect copy() {
        return new ShiftyDoppelgangerReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = creatureMor.getPermanent(game);
        Player player = game.getPlayer(source.getControllerId());
        if (creature == null || player == null
                || !creature.sacrifice(source, game)) {
            return false;
        }
        Card sourceCard = sourceMor.getCard(game);
        if (sourceCard != null) {
            player.moveCards(sourceCard, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
