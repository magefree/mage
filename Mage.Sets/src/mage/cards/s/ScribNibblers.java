package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class ScribNibblers extends CardImpl {

    public ScribNibblers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Exile the top card of target player's library. If it's a land card, you gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScribNibblersEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Landfall - Whenever a land enters the battlefield under your control, you may untap Scrib Nibblers.
        this.addAbility(new LandfallAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), true));
    }

    private ScribNibblers(final ScribNibblers card) {
        super(card);
    }

    @Override
    public ScribNibblers copy() {
        return new ScribNibblers(this);
    }
}

class ScribNibblersEffect extends OneShotEffect {

    ScribNibblersEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile the top card of target player's library. If it's a land card, you gain 1 life";
    }

    private ScribNibblersEffect(final ScribNibblersEffect effect) {
        super(effect);
    }

    @Override
    public ScribNibblersEffect copy() {
        return new ScribNibblersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (you == null || targetPlayer == null || !targetPlayer.getLibrary().hasCards()) {
            return false;
        }
        Card card = targetPlayer.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        you.moveCards(card, Zone.EXILED, source, game);
        if (card.isLand(game)) {
            you.gainLife(1, game, source);
        }
        return true;
    }
}
