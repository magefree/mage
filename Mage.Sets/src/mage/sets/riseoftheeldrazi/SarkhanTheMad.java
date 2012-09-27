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
package mage.sets.riseoftheeldrazi;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class SarkhanTheMad extends CardImpl<SarkhanTheMad> {

    public SarkhanTheMad(UUID ownerId) {
        super(ownerId, 214, "Sarkhan the Mad", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{R}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Sarkhan");
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(7)), false));

        this.color.setBlack(true);
        this.color.setRed(true);

        this.addAbility(new LoyaltyAbility(new SarkhanTheMadRevealAndDrawEffect(), 0));
        Target targetCreature = new TargetCreaturePermanent();
        Ability sacAbility = new LoyaltyAbility(new SarkhanTheMadSacEffect(), -2);
        sacAbility.addTarget(targetCreature);
        this.addAbility(sacAbility);
        Ability damageAbility = new LoyaltyAbility(new SarkhanTheMadDragonDamageEffect(), -4);
        damageAbility.addTarget(new TargetPlayer());
        this.addAbility(damageAbility);
    }

    public SarkhanTheMad(final SarkhanTheMad card) {
        super(card);
    }

    @Override
    public SarkhanTheMad copy() {
        return new SarkhanTheMad(this);
    }
}

class SarkhanTheMadRevealAndDrawEffect extends OneShotEffect<SarkhanTheMadRevealAndDrawEffect> {

    private static final String effectText = "Reveal the top card of your library and put it into your hand.  {this} deals damage to himself equal to that card's converted mana cost";

    SarkhanTheMadRevealAndDrawEffect ( ) {
        super(Outcome.DrawCard);
        staticText = effectText;
    }

    SarkhanTheMadRevealAndDrawEffect ( SarkhanTheMadRevealAndDrawEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() > 0) {
            Card card = player.getLibrary().removeFromTop(game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (card != null) {
                card.moveToZone(Zone.HAND, source.getId(), game, false);
                permanent.damage(card.getManaCost().convertedManaCost(), this.getId(), game, false, false);
                Cards cards = new CardsImpl();
                cards.add(card);
                player.revealCards("Sarkhan the Mad", cards, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public SarkhanTheMadRevealAndDrawEffect copy() {
        return new SarkhanTheMadRevealAndDrawEffect(this);
    }

}

class SarkhanTheMadSacEffect extends OneShotEffect<SarkhanTheMadSacEffect> {

    private static final String effectText = "Target creature's controller sacrifices it, then that player puts a 5/5 red Dragon creature token with flying onto the battlefield";

    SarkhanTheMadSacEffect ( ) {
        super(Outcome.Sacrifice);
        staticText = effectText;
    }

    SarkhanTheMadSacEffect ( SarkhanTheMadSacEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        if ( permanent != null ) {
            Player player = game.getPlayer(permanent.getControllerId());
            permanent.sacrifice(this.getId(), game);
            Token dragonToken = new DragonToken();
            dragonToken.putOntoBattlefield(1, game, this.getId(), player.getId());
        }
        return false;
    }

    @Override
    public SarkhanTheMadSacEffect copy() {
        return new SarkhanTheMadSacEffect(this);
    }

}

class SarkhanTheMadDragonDamageEffect extends OneShotEffect<SarkhanTheMadDragonDamageEffect> {

    private static final String effectText = "Each Dragon creature you control deals damage equal to its power to target player";
    private static final FilterControlledPermanent filter;

    static {
        filter = new FilterControlledPermanent();
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new SubtypePredicate("Dragon"));
    }

    SarkhanTheMadDragonDamageEffect ( ) {
        super(Outcome.Damage);
        staticText = effectText;
    }

    SarkhanTheMadDragonDamageEffect ( SarkhanTheMadDragonDamageEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> dragons = game.getBattlefield().getAllActivePermanents(filter, game);
        Player player = game.getPlayer(source.getTargets().getFirstTarget());
        if ( player != null && dragons != null && !dragons.isEmpty() ) {
            for ( Permanent dragon : dragons ) {
                player.damage(dragon.getPower().getValue(), dragon.getId(), game, true, false);
            }
            return true;
        }

        return false;
    }

    @Override
    public SarkhanTheMadDragonDamageEffect copy() {
        return new SarkhanTheMadDragonDamageEffect(this);
    }

}

class DragonToken extends mage.game.permanent.token.DragonToken {
    DragonToken ( ) {
        super();
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
    }
}
