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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class MistveilPlains extends CardImpl<MistveilPlains> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control two or more white permanents");
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public MistveilPlains(UUID ownerId) {
        super(ownerId, 275, "Mistveil Plains", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "SHM";
        this.subtype.add("Plains");

        // <i>({tap}: Add {W} to your mana pool.)</i>
        this.addAbility(new WhiteManaAbility());

        // Mistveil Plains enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {W}, {tap}: Put target card from your graveyard on the bottom of your library. Activate this ability only if you control two or more white permanents.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, 
                new MistveilPlainsGraveyardToLibraryEffect(), 
                new ManaCostsImpl("{W}"), 
                new ControlsPermanentCondition(filter, ControlsPermanentCondition.CountType.MORE_THAN, 1));
        ability.addTarget(new TargetCardInYourGraveyard());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public MistveilPlains(final MistveilPlains card) {
        super(card);
    }

    @Override
    public MistveilPlains copy() {
        return new MistveilPlains(this);
    }
}

class MistveilPlainsGraveyardToLibraryEffect extends OneShotEffect<MistveilPlainsGraveyardToLibraryEffect> {

    public MistveilPlainsGraveyardToLibraryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target card from your graveyard on the bottom of your library";
    }

    public MistveilPlainsGraveyardToLibraryEffect(final MistveilPlainsGraveyardToLibraryEffect effect) {
        super(effect);
    }

    @Override
    public MistveilPlainsGraveyardToLibraryEffect copy() {
        return new MistveilPlainsGraveyardToLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            return card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
        }
        return false;
    }
}
