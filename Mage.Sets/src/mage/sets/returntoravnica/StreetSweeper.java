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
package mage.sets.returntoravnica;
 
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;
 
/**
 *
 * @author LevelX2
 */
public class StreetSweeper extends CardImpl<StreetSweeper> {
 
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures with defender you control");
 
    static{
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }
 
    public StreetSweeper(UUID ownerId) {
        super(ownerId, 234, "Street Sweeper", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Construct");
 
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);
 

        // Whenever Street Sweeper attacks, destroy all Auras attached to target land.
        Ability ability = new AttacksTriggeredAbility(new StreetSweeperDestroyEffect(), false);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
 
    }
 
    public StreetSweeper(final StreetSweeper card) {
        super(card);
    }
 
    @Override
    public StreetSweeper copy() {
        return new StreetSweeper(this);
    }
}
 
class StreetSweeperDestroyEffect extends OneShotEffect<StreetSweeperDestroyEffect> {
 
    public StreetSweeperDestroyEffect() {
        super(Constants.Outcome.DestroyPermanent);
    }
 
    public StreetSweeperDestroyEffect(final StreetSweeperDestroyEffect effect) {
        super(effect);
    }
 
    @Override
    public StreetSweeperDestroyEffect copy() {
        return new StreetSweeperDestroyEffect(this);
    }
 
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if(permanent != null)
        {
            LinkedList<UUID> attachments = new LinkedList();
            attachments.addAll(permanent.getAttachments());
            for(UUID uuid : attachments)
            {
                Permanent aura = game.getPermanent(uuid);
                if(aura != null && aura.getSubtype().contains("Aura"))
                {
                    aura.destroy(source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }
 
    @Override
    public String getText(Mode mode) {
        return "destroy all Auras attached to target land";
    }
}