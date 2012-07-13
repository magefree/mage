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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continious.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class VaporSnare extends CardImpl<VaporSnare> {

    public VaporSnare(UUID ownerId) {
        super(ownerId, 44, "Vapor Snare", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Aura");

        this.color.setBlue(true);

        // Enchant creature
        // You control enchanted creature.
        // At the beginning of your upkeep, sacrifice Vapor Snare unless you return a land you control to its owner's hand.
        
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ControlEnchantedEffect()));
        
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new VaporSnareEffect(), Constants.TargetController.YOU, false));
    }

    public VaporSnare(final VaporSnare card) {
        super(card);
    }

    @Override
    public VaporSnare copy() {
        return new VaporSnare(this);
    }
}

class VaporSnareEffect extends OneShotEffect<VaporSnareEffect> {
    
    private static final FilterControlledPermanent filter;
    private static final String effectText = "sacrifice {this} unless you return a land you control to its owner's hand";

    static {
        filter = new FilterControlledPermanent("land");
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    VaporSnareEffect( ) {
        super(Constants.Outcome.Sacrifice);
        staticText = effectText;
    }

    VaporSnareEffect(VaporSnareEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean targetChosen = false;
        Player player = game.getPlayer(source.getControllerId());
        TargetPermanent target = new TargetPermanent(1, 1, filter, false);

        if (target.canChoose(player.getId(), game)) {
            player.choose(Constants.Outcome.Sacrifice, target, source.getSourceId(), game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());

            if ( permanent != null ) {
                targetChosen = true;
                permanent.moveToZone(Constants.Zone.HAND, this.getId(), game, false);
            }
        }

        if ( !targetChosen ) {
            new SacrificeSourceEffect().apply(game, source);
        }

        return false;
    }

    @Override
    public VaporSnareEffect copy() {
        return new VaporSnareEffect(this);
    }
}