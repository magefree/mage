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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;

/**
 *
 * @author emerald000
 */
public class MarangRiverProwler extends CardImpl {

    public MarangRiverProwler(UUID ownerId) {
        super(ownerId, 40, "Marang River Prowler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Human");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Marang River Prowler can't block and can't be blocked.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockSourceEffect(Duration.WhileOnBattlefield));
        Effect effect = new CantBeBlockedSourceEffect();
        effect.setText("and can't be blocked");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // You may cast Marang River Prowler from your graveyard as long as you control a black or green permanent.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new MarangRiverProwlerCastEffect()));
    }

    public MarangRiverProwler(final MarangRiverProwler card) {
        super(card);
    }

    @Override
    public MarangRiverProwler copy() {
        return new MarangRiverProwler(this);
    }
}

class MarangRiverProwlerCastEffect extends AsThoughEffectImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a black or green permanent");
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK), new ColorPredicate(ObjectColor.GREEN)));
    }

    MarangRiverProwlerCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard as long as you control a black or green permanent";
    }

    MarangRiverProwlerCastEffect(final MarangRiverProwlerCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MarangRiverProwlerCastEffect copy() {
        return new MarangRiverProwlerCastEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null 
                    && card.getOwnerId().equals(affectedControllerId) 
                    && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                    && game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) > 0) {
                return true;
            }
        }
        return false;
    }
}
