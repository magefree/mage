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
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author L_J
 */
public class PsychicAllergy extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("two Islands");

    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
    }

    public PsychicAllergy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");

        // As Psychic Allergy enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Damage)));

        // At the beginning of each opponent's upkeep, Psychic Allergy deals X damage to that player, where X is the number of nontoken permanents of the chosen color he or she controls.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PsychicAllergyEffect(), TargetController.OPPONENT, false));

        // At the beginning of your upkeep, destroy Psychic Allergy unless you sacrifice two Islands.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, 
            new DoUnlessControllerPaysEffect(new DestroySourceEffect(), 
            new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, false))).setText("destroy {this} unless you sacrifice two Islands"), 
            TargetController.YOU, 
            false));
    }

    public PsychicAllergy(final PsychicAllergy card) {
        super(card);
    }

    @Override
    public PsychicAllergy copy() {
        return new PsychicAllergy(this);
    }
}

class PsychicAllergyEffect extends OneShotEffect {
    
    public PsychicAllergyEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to that player, where X is the number of nontoken permanents of the chosen color he or she controls";
    }
    
    public PsychicAllergyEffect(PsychicAllergyEffect copy) {
        super(copy);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            FilterPermanent filter = new FilterPermanent();
            filter.add(new ColorPredicate((ObjectColor) game.getState().getValue(source.getSourceId() + "_color")));
            filter.add(Predicates.not(new TokenPredicate()));        
            int damage = game.getBattlefield().countAll(filter, player.getId(), game);
            player.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public PsychicAllergyEffect copy() {
        return new PsychicAllergyEffect(this);
    }
}
