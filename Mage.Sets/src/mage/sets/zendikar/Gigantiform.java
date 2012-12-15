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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants;
import mage.Constants.AttachmentType;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class Gigantiform extends CardImpl<Gigantiform> {

    public Gigantiform(UUID ownerId) {
        super(ownerId, 162, "Gigantiform", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature is 8/8 and has trample.
        this.addAbility(new GigantiformAbility());
        // When Gigantiform enters the battlefield, if it was kicked, you may search your library for a card named Gigantiform, put it onto the battlefield, then shuffle your library.
        this.addAbility(new GigantiformTriggeredAbility());
    }

    public Gigantiform(final Gigantiform card) {
        super(card);
    }

    @Override
    public Gigantiform copy() {
        return new Gigantiform(this);
    }
}

class GigantiformAbility extends StaticAbility<GigantiformAbility> {

    public GigantiformAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA));
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(8, 8, Duration.WhileOnBattlefield));
        this.addEffect(new GainAbilityAttachedEffect(ability, AttachmentType.AURA));
    }

    public GigantiformAbility(GigantiformAbility ability) {
        super(ability);
    }

    @Override
    public GigantiformAbility copy() {
        return new GigantiformAbility(this);
    }

    @Override
    public String getRule() {
        return "Enchanted creature is 8/8 and has trample.";
    }
}

class GigantiformTriggeredAbility extends TriggeredAbilityImpl<GigantiformTriggeredAbility> {

    public GigantiformTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GigantiformEffect());
    }

    public GigantiformTriggeredAbility(final GigantiformTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GigantiformTriggeredAbility copy() {
        return new GigantiformTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(this.getSourceId())
                && ((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD
                && KickedCondition.getInstance().apply(game, this)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When Gigantiform enters the battlefield, if it was kicked, you may search your library for a card named Gigantiform, put it onto the battlefield, then shuffle your library.";
    }
}

class GigantiformEffect extends OneShotEffect<GigantiformEffect> {

    private static final String message = "Do you wish to search your library for a card named Gigantiform, put it onto the battlefield, then shuffle your library?";
    private static final FilterCard filter = new FilterCard("card named Gigantiform");

    static {
        filter.add(new NamePredicate("Gigantiform"));
    }

    public GigantiformEffect() {
        super(Outcome.PutCardInPlay);
    }

    public GigantiformEffect(final GigantiformEffect effect) {
        super(effect);
    }

    @Override
    public GigantiformEffect copy() {
        return new GigantiformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player != null && player.chooseUse(Outcome.PutCardInPlay, message, game) && player.searchLibrary(target, game)) {
            Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), source.getControllerId());
            }
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
