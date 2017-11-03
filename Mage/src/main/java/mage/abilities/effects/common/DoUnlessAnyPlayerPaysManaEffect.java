/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class DoUnlessAnyPlayerPaysManaEffect extends ManaEffect {

    private final ManaEffect manaEffect;
    private final Cost cost;
    private final String chooseUseText;

    public DoUnlessAnyPlayerPaysManaEffect(ManaEffect effect, Cost cost, String chooseUseText) {
        this.manaEffect = effect;
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoUnlessAnyPlayerPaysManaEffect(final DoUnlessAnyPlayerPaysManaEffect effect) {
        super(effect);
        this.manaEffect = (ManaEffect) effect.manaEffect.copy();
        this.cost = effect.cost.copy();
        this.chooseUseText = effect.chooseUseText;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            String message = CardUtil.replaceSourceName(chooseUseText, sourceObject.getName());
            boolean result = true;
            boolean doEffect = true;
            // check if any player is willing to pay
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && cost.canPay(source, source.getSourceId(), player.getId(), game) && player.chooseUse(Outcome.Detriment, message, source, game)) {
                    cost.clearPaid();
                    if (cost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                        if (!game.isSimulation()) {
                            game.informPlayers(player.getLogName() + " pays the cost to prevent the effect");
                        }
                        doEffect = false;
                    }
                }
            }
            // do the effects if nobody paid
            if (doEffect) {
                return manaEffect.apply(game, source);
            }
            return result;
        }
        return false;
    }

    protected Player getPayingPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return manaEffect.getText(mode) + " unless any player pays " + cost.getText();
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return manaEffect.getMana(game, source);
    }

    @Override
    public ManaEffect copy() {
        return new DoUnlessAnyPlayerPaysManaEffect(this);
    }

}
