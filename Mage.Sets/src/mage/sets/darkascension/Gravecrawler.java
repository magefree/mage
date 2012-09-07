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
package mage.sets.darkascension;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class Gravecrawler extends CardImpl<Gravecrawler> {

    public Gravecrawler(UUID ownerId) {
        super(ownerId, 64, "Gravecrawler", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Gravecrawler can't block.
        this.addAbility(new CantBlockAbility());

        // You may cast Gravecrawler from your graveyard as long as you control a Zombie.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GravecrawlerPlayEffect()));

    }

    public Gravecrawler(final Gravecrawler card) {
        super(card);
    }

    @Override
    public Gravecrawler copy() {
        return new Gravecrawler(this);
    }
}

class GravecrawlerPlayEffect extends AsThoughEffectImpl<GravecrawlerPlayEffect> {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("zombie");

    static {
        filter.add(new SubtypePredicate("Zombie"));
    }

    public GravecrawlerPlayEffect() {
        super(Constants.AsThoughEffectType.CAST, Constants.Duration.EndOfGame, Constants.Outcome.Benefit);
        staticText = "You may cast Gravecrawler from your graveyard as long as you control a Zombie";
    }

    public GravecrawlerPlayEffect(final GravecrawlerPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GravecrawlerPlayEffect copy() {
        return new GravecrawlerPlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && game.getState().getZone(source.getSourceId()) == Constants.Zone.GRAVEYARD) {
                if (game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0)
                    return true;
            }
        }
        return false;
    }

}