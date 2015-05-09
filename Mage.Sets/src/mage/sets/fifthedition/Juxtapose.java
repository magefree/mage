/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.fifthedition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author Quercitron
 */
public class Juxtapose extends CardImpl {

    public Juxtapose(UUID ownerId) {
        super(ownerId, 95, "Juxtapose", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "5ED";

        this.color.setBlue(true);

        // You and target player exchange control of the creature you each control with the highest converted mana cost. Then exchange control of artifacts the same way. If two or more permanents a player controls are tied for highest cost, their controller chooses one of them.
        this.getSpellAbility().addEffect(new JuxtaposeEffect(new FilterCreaturePermanent(), "You and target player exchange control of the creature you each control with the highest converted mana cost."));
        this.getSpellAbility().addEffect(new JuxtaposeEffect(new FilterArtifactPermanent(), "Then exchange control of artifacts the same way. If two or more permanents a player controls are tied for highest cost, their controller chooses one of them."));
        this.getSpellAbility().addTarget(new TargetPlayer() );
    }

    public Juxtapose(final Juxtapose card) {
        super(card);
    }

    @Override
    public Juxtapose copy() {
        return new Juxtapose(this);
    }
}

// effect is based on ExchangeControlTargetEffect
class JuxtaposeEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;
    
    private final Map<UUID, Integer> zoneChangeCounter;
    private final Map<UUID, UUID> lockedControllers;
    
    public JuxtaposeEffect(FilterPermanent filter, String text) {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = text;
        this.filter = filter;
        
        this.zoneChangeCounter = new HashMap<>();
        this.lockedControllers = new HashMap<>();
    }
    
    public JuxtaposeEffect(final JuxtaposeEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.zoneChangeCounter = new HashMap<>(effect.zoneChangeCounter);
        this.lockedControllers = new HashMap<>(effect.lockedControllers);
    }

    @Override
    public JuxtaposeEffect copy() {
        return new JuxtaposeEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject =game.getCard(source.getSourceId());
        
        if (you != null && targetPlayer != null) {
            Permanent permanent1 = chooseOnePermanentsWithTheHighestCMC(game, you, filter);
            Permanent permanent2 = chooseOnePermanentsWithTheHighestCMC(game, targetPlayer, filter);

            if (permanent1 != null && permanent2 != null) {
                // exchange works only for two different controllers
                if (permanent1.getControllerId().equals(permanent2.getControllerId())) {
                    // discard effect if controller of both permanents is the same
                    discard();
                    return;
                }
                this.lockedControllers.put(permanent1.getId(), permanent2.getControllerId());
                this.zoneChangeCounter.put(permanent1.getId(), permanent1.getZoneChangeCounter(game));
                this.lockedControllers.put(permanent2.getId(), permanent1.getControllerId());
                this.zoneChangeCounter.put(permanent2.getId(), permanent2.getZoneChangeCounter(game));
                
                permanent1.changeControllerId(targetPlayer.getId(), game);
                permanent2.changeControllerId(you.getId(), game);
                game.informPlayers(new StringBuilder(sourceObject != null ? sourceObject.getLogName() : "").append(": ").append(you.getLogName())
                        .append(" and ").append(targetPlayer.getLogName()).append(" exchange control of ").append(permanent1.getLogName())
                        .append(" and ").append(permanent2.getName()).toString());
            } else {
                // discard if there are less than 2 permanents
                discard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> toDelete = new HashSet<>();
        for (Map.Entry<UUID, Integer> entry : zoneChangeCounter.entrySet()) {
            Permanent permanent = game.getPermanent(entry.getKey());
            if (permanent == null || permanent.getZoneChangeCounter(game) != entry.getValue()) {
                // controll effect cease if the same permanent is no longer on the battlefield
                toDelete.add(entry.getKey());
                continue;
            }
            permanent.changeControllerId(lockedControllers.get(permanent.getId()), game);
        }
        if (!toDelete.isEmpty()) {
            for (UUID uuid : toDelete) {
                zoneChangeCounter.remove(uuid);
            }
            if (zoneChangeCounter.isEmpty()) {
                discard();
                return false;
            }
        }
        return true;
    }
    
    private Permanent chooseOnePermanentsWithTheHighestCMC(Game game, Player player, FilterPermanent filter) {
        List<Permanent> permanents = getPermanentsWithTheHighestCMC(game, player.getId(), filter);
        return chooseOnePermanent(game, player, permanents);
    }
    
    private List<Permanent> getPermanentsWithTheHighestCMC(Game game, UUID playerId, FilterPermanent filter) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, playerId, game);
        int highestCMC = -1;
        for (Permanent permanent : permanents) {
            if (highestCMC < permanent.getManaCost().convertedManaCost()) {
                highestCMC = permanent.getManaCost().convertedManaCost();
            }
        }
        List<Permanent> result = new ArrayList<>();
        for (Permanent permanent : permanents) {
            if (permanent.getManaCost().convertedManaCost() == highestCMC) {
                result.add(permanent);
            }
        }
        return result;
    }

    private Permanent chooseOnePermanent(Game game, Player player, List<Permanent> permanents) {
        Permanent permanent = null;
        if (permanents.size() == 1) {
            permanent = permanents.iterator().next();
        } else if (permanents.size() > 1) {
            Cards cards = new CardsImpl();
            for (Permanent card : permanents) {
                cards.add(card);
            }

            TargetCard targetCard = new TargetCard(Zone.BATTLEFIELD, new FilterCard());
            if (player.choose(Outcome.Benefit, cards, targetCard, game)) {
                permanent = game.getPermanent(targetCard.getFirstTarget());
            }
        }
        return permanent;
    }
    
}
