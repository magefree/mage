package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.FrameStyle;
import mage.cards.repository.TokenInfo;
import mage.cards.repository.TokenRepository;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;

/**
 * Special emblem to enable the Rad Counter inherent trigger
 * with an actual source, to display image on the stack.
 *
 * @author Susucr
 */
public class RadiationEmblem extends Emblem {

    public RadiationEmblem() {
        super("Radiation");
        this.frameStyle = FrameStyle.M15_NORMAL;

        this.getAbilities().add(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfPreCombatMainTriggeredAbility(Zone.ALL, new RadiationEffect(), TargetController.YOU, false, false),
                RadiationCondition.instance,
                "At the beginning of your precombat main phase, if you have any rad counters, "
                        + "mill that many cards. For each nonland card milled this way, you lose 1 life and a rad counter."
        ));

        TokenInfo foundInfo = TokenRepository.instance.findPreferredTokenInfoForXmage(TokenRepository.XMAGE_IMAGE_NAME_RADIATION, null);
        if (foundInfo != null) {
            this.setExpansionSetCode(foundInfo.getSetCode());
            this.setUsesVariousArt(false);
            this.setCardNumber("");
            this.setImageFileName(""); // use default
            this.setImageNumber(foundInfo.getImageNumber());
        } else {
            // how-to fix: add emblem to the tokens-database
            throw new IllegalArgumentException("Wrong code usage: can't find xmage token info for: " + TokenRepository.XMAGE_IMAGE_NAME_RADIATION);
        }
    }

    private RadiationEmblem(final RadiationEmblem card) {
        super(card);
    }

    @Override
    public RadiationEmblem copy() {
        return new RadiationEmblem(this);
    }
}

enum RadiationCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getCounters().getCount(CounterType.RAD) > 0;
    }
}

/**
 * 725.1. Rad counters are a kind of counter a player can have (see rule 122, "Counters").
 * There is an inherent triggered ability associated with rad counters. This ability has no
 * source and is controlled by the active player. This is an exception to rule 113.8. The
 * full text of this ability is "At the beginning of each player's precombat main phase, if
 * that player has one or more rad counters, that player mills a number of cards equal to
 * the number of rad counters they have. For each nonland card milled this way, that player
 * loses 1 life and removes one rad counter from themselves."
 */
class RadiationEffect extends OneShotEffect {

    RadiationEffect() {
        super(Outcome.Neutral);
        staticText = "mill that many cards. For each nonland card milled this way, "
                + "you lose 1 life and remove one rad counter.";
    }

    private RadiationEffect(final RadiationEffect effect) {
        super(effect);
    }

    @Override
    public RadiationEffect copy() {
        return new RadiationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = player.getCounters().getCount(CounterType.RAD);
        Cards milled = player.millCards(amount, source, game);
        int countNonLand = milled.count(StaticFilters.FILTER_CARD_NON_LAND, player.getId(), source, game);
        if (countNonLand > 0) {
            // TODO: support gaining life instead with [[Strong, the Brutish Thespian]]
            player.loseLife(countNonLand, game, source, false);
            player.removeCounters(CounterType.RAD.getName(), countNonLand, source, game);
        }
        return true;
    }
}