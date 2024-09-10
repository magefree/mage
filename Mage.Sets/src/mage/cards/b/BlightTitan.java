package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlightTitan extends CardImpl {

    public BlightTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Blight Titan enters the battlefield or attacks, mill two cards, then incubate X, where X is the number of creature cards in your graveyard.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new BlightTitanEffect());
        this.addAbility(ability);
    }

    private BlightTitan(final BlightTitan card) {
        super(card);
    }

    @Override
    public BlightTitan copy() {
        return new BlightTitan(this);
    }
}

class BlightTitanEffect extends OneShotEffect {

    BlightTitanEffect() {
        super(Outcome.Benefit);
        staticText = ", then incubate X, where X is the number of creature cards in your graveyard";
    }

    private BlightTitanEffect(final BlightTitanEffect effect) {
        super(effect);
    }

    @Override
    public BlightTitanEffect copy() {
        return new BlightTitanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = Optional.of(source.getControllerId())
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(graveyard -> graveyard.count(StaticFilters.FILTER_CARD_CREATURE, game))
                .orElse(0);
        return IncubateEffect.doIncubate(count, game, source);
    }
}
