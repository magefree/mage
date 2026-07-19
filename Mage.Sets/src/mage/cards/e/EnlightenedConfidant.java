package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 * @author muz
 */
public final class EnlightenedConfidant extends CardImpl {

    public EnlightenedConfidant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, if you gained life this turn, surveil 1. If you put a card with mana value less than or equal to the amount of life you gained this turn into your graveyard this way, put that card into your hand.
        TriggeredAbility ability = new BeginningOfEndStepTriggeredAbility(new EnlightenedConfidantEffect());
        ability.withInterveningIf(YouGainedLifeCondition.getZero());
        ability.addHint(ControllerGainedLifeCount.getHint());
        this.addAbility(ability, new PlayerGainedLifeWatcher());
    }

    private EnlightenedConfidant(final EnlightenedConfidant card) {
        super(card);
    }

    @Override
    public EnlightenedConfidant copy() {
        return new EnlightenedConfidant(this);
    }
}

class EnlightenedConfidantEffect extends OneShotEffect {

    EnlightenedConfidantEffect() {
        super(Outcome.Benefit);
        this.staticText = "surveil 1. If you put a card with mana value less than or equal to the amount "
            + "of life you gained this turn into your graveyard this way, put that card into your hand";
    }

    private EnlightenedConfidantEffect(final EnlightenedConfidantEffect effect) {
        super(effect);
    }

    @Override
    public EnlightenedConfidantEffect copy() {
        return new EnlightenedConfidantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        int lifeGained = ControllerGainedLifeCount.instance.calculate(game, source, this);
        if (lifeGained < 1) {
            return false;
        }

        Card surveilledCard = player.getLibrary().getFromTop(game);
        Player.SurveilResult result = player.doSurveil(1, source, game);
        if (!result.hasSurveilled() || result.getNumberPutInGraveyard() == 0 || surveilledCard == null) {
            return result.hasSurveilled();
        }

        if (player.getGraveyard().contains(surveilledCard.getId()) && surveilledCard.getManaValue() <= lifeGained) {
            return player.moveCards(surveilledCard, Zone.HAND, source, game);
        }
        return true;
    }
}
