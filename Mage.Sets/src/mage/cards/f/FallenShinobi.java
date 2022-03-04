package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FallenShinobi extends CardImpl {

    public FallenShinobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Ninjutsu {2}{U}{B}
        this.addAbility(new NinjutsuAbility("{2}{U}{B}"));

        // Whenever Fallen Shinobi deals combat damage to a player, that player exiles the top two cards of their library. Until end of turn, you may play those cards without paying their mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new FallenShinobiEffect(), false, true
        ));
    }

    private FallenShinobi(final FallenShinobi card) {
        super(card);
    }

    @Override
    public FallenShinobi copy() {
        return new FallenShinobi(this);
    }
}

class FallenShinobiEffect extends OneShotEffect {

    FallenShinobiEffect() {
        super(Outcome.Benefit);
        staticText = "that player exiles the top two cards of their library. "
                + "Until end of turn, you may play those cards without paying their mana costs.";
    }

    private FallenShinobiEffect(final FallenShinobiEffect effect) {
        super(effect);
    }

    @Override
    public FallenShinobiEffect copy() {
        return new FallenShinobiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, player.getLibrary().getTopCards(game, 2),
                TargetController.YOU, Duration.EndOfTurn, true, false, false);
    }
}
