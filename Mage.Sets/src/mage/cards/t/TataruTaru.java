package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TataruTaru extends CardImpl {

    public TataruTaru(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Tataru Taru enters, you draw a card and target opponent may draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1, true));
        ability.addEffect(new TataruTaruEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Scions' Secretary -- Whenever an opponent draws a card, if it isn't that player's turn, create a tapped Treasure token. This ability triggers only once each turn.
        this.addAbility(new DrawCardOpponentTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 1, true), false, false
        ).setTriggersLimitEachTurn(1).withInterveningIf(TataruTaruCondition.instance).withFlavorWord("Scions' Secretary"));
    }

    private TataruTaru(final TataruTaru card) {
        super(card);
    }

    @Override
    public TataruTaru copy() {
        return new TataruTaru(this);
    }
}

enum TataruTaruCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return !CardUtil
                .getEffectValueFromAbility(source, "playerDrew", UUID.class)
                .map(game::isActivePlayer)
                .orElse(true);
    }

    @Override
    public String toString() {
        return "it isn't that player's turn";
    }
}

class TataruTaruEffect extends OneShotEffect {

    TataruTaruEffect() {
        super(Outcome.Benefit);
        staticText = "and target opponent may draw a card";
    }

    private TataruTaruEffect(final TataruTaruEffect effect) {
        super(effect);
    }

    @Override
    public TataruTaruEffect copy() {
        return new TataruTaruEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPlayer)
                .filter(player -> player.chooseUse(Outcome.DrawCard, "Draw a card?", source, game))
                .map(player -> player.drawCards(1, source, game))
                .orElse(0) > 0;
    }
}
