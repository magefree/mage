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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class DemigodOfRevenge extends CardImpl<DemigodOfRevenge> {

    public DemigodOfRevenge(UUID ownerId) {
        super(ownerId, 183, "Demigod of Revenge", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B/R}{B/R}{B/R}{B/R}{B/R}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Spirit");
        this.subtype.add("Avatar");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When you cast Demigod of Revenge, return all cards named Demigod of Revenge from your graveyard to the battlefield.
        this.addAbility(new DemigodOfRevengeTriggeredAbility());
    }

    public DemigodOfRevenge(final DemigodOfRevenge card) {
        super(card);
    }

    @Override
    public DemigodOfRevenge copy() {
        return new DemigodOfRevenge(this);
    }
}

class DemigodOfRevengeTriggeredAbility extends TriggeredAbilityImpl<DemigodOfRevengeTriggeredAbility> {

    public DemigodOfRevengeTriggeredAbility() {
        super(Zone.STACK, new DemigodOfRevengeReturnEffect(), false);
    }

    public DemigodOfRevengeTriggeredAbility(final DemigodOfRevengeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DemigodOfRevengeTriggeredAbility copy() {
        return new DemigodOfRevengeTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.SPELL_CAST) && event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you cast Demigod of Revenge, " + super.getRule();
    }
}

class DemigodOfRevengeReturnEffect extends OneShotEffect<DemigodOfRevengeReturnEffect> {

    private static final FilterCard filter = new FilterCard();
    static {
        filter.add(new NamePredicate("Demigod of Revenge"));
    }

    public DemigodOfRevengeReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return all cards named Demigod of Revenge from your graveyard to the battlefield";
    }

    public DemigodOfRevengeReturnEffect(final DemigodOfRevengeReturnEffect effect) {
        super(effect);
    }

    @Override
    public DemigodOfRevengeReturnEffect copy() {
        return new DemigodOfRevengeReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card creature : player.getGraveyard().getCards(filter, game)) {
                creature.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), source.getControllerId());
            }
            return true;
        }
        return false;
    }
}
