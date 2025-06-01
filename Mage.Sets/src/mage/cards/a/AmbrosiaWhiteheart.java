package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetImpl;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmbrosiaWhiteheart extends CardImpl {

    public AmbrosiaWhiteheart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Ambrosia Whiteheart enters, you may return another permanent you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AmbrosiaWhiteheartEffect()));

        // Landfall -- Whenever a land you control enters, Ambrosia Whiteheart gets +1/+0 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)));
    }

    private AmbrosiaWhiteheart(final AmbrosiaWhiteheart card) {
        super(card);
    }

    @Override
    public AmbrosiaWhiteheart copy() {
        return new AmbrosiaWhiteheart(this);
    }
}

class AmbrosiaWhiteheartEffect extends OneShotEffect {

    AmbrosiaWhiteheartEffect() {
        super(Outcome.Benefit);
        staticText = "you may return another permanent you control to its owner's hand";
    }

    private AmbrosiaWhiteheartEffect(final AmbrosiaWhiteheartEffect effect) {
        super(effect);
    }

    @Override
    public AmbrosiaWhiteheartEffect copy() {
        return new AmbrosiaWhiteheartEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_PERMANENT, true
        );
        player.choose(outcome, target, source, game);
        return Optional
                .ofNullable(target)
                .map(TargetImpl::getFirstTarget)
                .map(game::getCard)
                .map(card -> player.moveCards(card, Zone.HAND, source, game))
                .orElse(false);
    }
}
