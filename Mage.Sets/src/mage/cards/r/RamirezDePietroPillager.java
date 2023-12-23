package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RamirezDePietroPillager extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.PIRATE, "Pirates");

    public RamirezDePietroPillager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Ramirez DePietro, Pillager enters the battlefield, you lose 2 life and create two Treasure tokens.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(2));
        ability.addEffect(new CreateTokenEffect(new TreasureToken(), 2).setText("and create two Treasure tokens"));
        this.addAbility(ability);

        // Whenever one or more Pirates you control deal combat damage to a player, exile the top card of that player's library. You may cast that card for as long as it remains exiled.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(Zone.BATTLEFIELD, new RamirezDePietroPillagerEffect(),
                filter, SetTargetPointer.PLAYER, false));
    }

    private RamirezDePietroPillager(final RamirezDePietroPillager card) {
        super(card);
    }

    @Override
    public RamirezDePietroPillager copy() {
        return new RamirezDePietroPillager(this);
    }
}

class RamirezDePietroPillagerEffect extends OneShotEffect {

    RamirezDePietroPillagerEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of that player's library. You may cast that card for as long as it remains exiled.";
    }

    private RamirezDePietroPillagerEffect(final RamirezDePietroPillagerEffect effect) {
        super(effect);
    }

    @Override
    public RamirezDePietroPillagerEffect copy() {
        return new RamirezDePietroPillagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(game, source, card, Duration.Custom, false, source.getControllerId(), null);
        return true;
    }
}
