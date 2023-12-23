
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Plopman
 */
public final class MoxDiamond extends CardImpl {

    public MoxDiamond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // If Mox Diamond would enter the battlefield, you may discard a land card instead. If you do, put Mox Diamond onto the battlefield. If you don't, put it into its owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new MoxDiamondReplacementEffect()));
        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private MoxDiamond(final MoxDiamond card) {
        super(card);
    }

    @Override
    public MoxDiamond copy() {
        return new MoxDiamond(this);
    }
}

class MoxDiamondReplacementEffect extends ReplacementEffectImpl {

    public MoxDiamondReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If {this} would enter the battlefield, you may discard a land card instead. If you do, put {this} onto the battlefield. If you don't, put it into its owner's graveyard";
    }

    private MoxDiamondReplacementEffect(final MoxDiamondReplacementEffect effect) {
        super(effect);
    }

    @Override
    public MoxDiamondReplacementEffect copy() {
        return new MoxDiamondReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCardInHand target = new TargetCardInHand(new FilterLandCard());
            Cost cost = new DiscardTargetCost(target);
            if (cost.canPay(source, source, source.getControllerId(), game)
                    && player.chooseUse(outcome, "Discard land? (Otherwise Mox Diamond goes to graveyard)", source, game)
                    && player.chooseTarget(Outcome.Discard, target, source, game)) {
                player.discard(game.getCard(target.getFirstTarget()), false, source, game);
                return false;
            } else {
                Permanent permanent = game.getPermanentEntering(event.getTargetId());
                if (permanent != null) {
                    player.moveCards(permanent, Zone.GRAVEYARD, source, game);
                }
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId());
    }

}
