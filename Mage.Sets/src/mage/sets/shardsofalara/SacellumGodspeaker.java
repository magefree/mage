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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Plopman
 */
public class SacellumGodspeaker extends CardImpl<SacellumGodspeaker> {
    
    public SacellumGodspeaker(UUID ownerId) {
        super(ownerId, 146, "Sacellum Godspeaker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Reveal any number of creature cards with power 5 or greater from your hand. Add {G} to your mana pool for each card revealed this way.
        this.addAbility(new SimpleManaAbility(Constants.Zone.BATTLEFIELD, new SacellumGodspeakerEffect(), new TapSourceCost()));
    }

    public SacellumGodspeaker(final SacellumGodspeaker card) {
        super(card);
    }

    @Override
    public SacellumGodspeaker copy() {
        return new SacellumGodspeaker(this);
    }
}


class SacellumGodspeakerEffect extends ManaEffect<SacellumGodspeakerEffect> {

    
    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with power 5 or greater from your hand");
    static {
        filter.add(new PowerPredicate(Filter.ComparisonType.GreaterThan, 4));
    }
    
    public SacellumGodspeakerEffect() {
        super();
        staticText = "Reveal any number of creature cards with power 5 or greater from your hand. Add {G} to your mana pool for each card revealed this way";
    }

    public SacellumGodspeakerEffect(final SacellumGodspeakerEffect effect) {
        super(effect);
    }

    @Override
    public SacellumGodspeakerEffect copy() {
        return new SacellumGodspeakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetCardInHand target = new TargetCardInHand(0,Integer.MAX_VALUE, filter);
        if (target.choose(Constants.Outcome.Benefit, source.getControllerId(), source.getId(), game)) {
            game.getPlayer(source.getControllerId()).getManaPool().addMana(Mana.GreenMana(target.getTargets().size()), game, source);
            return true;
        }
        return false;
    }
}