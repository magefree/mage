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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class TorrentElemental extends CardImpl {

    public TorrentElemental(UUID ownerId) {
        super(ownerId, 56, "Torrent Elemental", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Elemental");
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Torrent Elemental attacks, tap all creatures defending player controls.
        Effect effect = new TapAllTargetPlayerControlsEffect(new FilterCreaturePermanent());
        effect.setText("tap all creatures defending player controls.");
        this.addAbility(new AttacksTriggeredAbility(effect, false, null, SetTargetPointer.PLAYER));
        // {3}{B/G}{B/G}: Put Torrent Elemental from exile onto the battlefield tapped. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.EXILED, new ReturnSourceFromExileToBattlefieldEffect(true), new ManaCostsImpl("{3}{B/G}{B/G}"));
        this.addAbility(ability);

    }

    public TorrentElemental(final TorrentElemental card) {
        super(card);
    }

    @Override
    public TorrentElemental copy() {
        return new TorrentElemental(this);
    }
}

class ReturnSourceFromExileToBattlefieldEffect extends OneShotEffect {

    private boolean tapped;
    private boolean ownerControl;

    public ReturnSourceFromExileToBattlefieldEffect() {
        this(false);
    }

    public ReturnSourceFromExileToBattlefieldEffect(boolean tapped) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
        setText();
    }
    public ReturnSourceFromExileToBattlefieldEffect(boolean tapped, boolean ownerControl) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
        this.ownerControl = ownerControl;
        setText();
    }

    public ReturnSourceFromExileToBattlefieldEffect(final ReturnSourceFromExileToBattlefieldEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.ownerControl = effect.ownerControl;
    }

    @Override
    public ReturnSourceFromExileToBattlefieldEffect copy() {
        return new ReturnSourceFromExileToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getState().getZone(source.getSourceId()).equals(Zone.EXILED)) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }

        Player player;
        if (ownerControl) {
            player = game.getPlayer(card.getOwnerId());
        } else {
            player = game.getPlayer(source.getControllerId());
        }
        if (player == null) {
            return false;
        }

        return player.putOntoBattlefieldWithInfo(card, game, Zone.EXILED, source.getSourceId(), tapped);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("Put {this} from exile onto the battlefield");
        if (tapped) {
            sb.append(" tapped");
        }
        if (ownerControl) {
               sb.append(" under its owner's control");
        }
        staticText = sb.toString();
    }

}
