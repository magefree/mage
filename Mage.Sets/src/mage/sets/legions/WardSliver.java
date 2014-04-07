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
package mage.sets.legions;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author cbt33, noxx (Riders of Gavony)
 */
public class WardSliver extends CardImpl<WardSliver> {

    static FilterPermanent filter = new FilterPermanent("chosen color");
    
    
    public WardSliver(UUID ownerId) {
        super(ownerId, 25, "Ward Sliver", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "LGN";
        this.subtype.add("Sliver");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        
        // As Ward Sliver enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new WardSliverEffect()));

        // All Slivers have protection from the chosen color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WardSliverGainAbilityControlledEffect()));
        
    }

    public WardSliver(final WardSliver card) {
        super(card);
    }

    @Override
    public WardSliver copy() {
        return new WardSliver(this);
    }
}

class WardSliverEffect extends OneShotEffect<WardSliverEffect> {

    public WardSliverEffect() {
        super(Outcome.BoostCreature);
        staticText = "choose a color";
    }

    public WardSliverEffect(final WardSliverEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            Choice colorChoice = new ChoiceColor();
            colorChoice.setMessage("Choose color");
            while (!player.choose(Outcome.BoostCreature, colorChoice, game)) {
                if (!player.isInGame()) {
                    return false;
                }
            }
            if (colorChoice.getChoice() != null) {
                game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
                game.getState().setValue(permanent.getId() + "_color", colorChoice.getChoice());
                permanent.addInfo("chosen color", "<i>Chosen color: " + colorChoice.getChoice().toString() + "</i>");
            }
        }
        return false;
    }

    @Override
    public WardSliverEffect copy() {
        return new WardSliverEffect(this);
    }

}

class WardSliverGainAbilityControlledEffect extends ContinuousEffectImpl<WardSliverGainAbilityControlledEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Slivers");

    static {
        filter.add(new SubtypePredicate("Sliver"));
    }

    protected FilterPermanent protectionFilter;

    public WardSliverGainAbilityControlledEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Slivers have protection from the chosen color";
    }

    public WardSliverGainAbilityControlledEffect(final WardSliverGainAbilityControlledEffect effect) {
        super(effect);
        protectionFilter = effect.protectionFilter;
    }

    @Override
    public WardSliverGainAbilityControlledEffect copy() {
        return new WardSliverGainAbilityControlledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (protectionFilter == null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                String color = (String) game.getState().getValue(permanent.getId() + "_color");
                if (color != null) {
                    protectionFilter = new FilterPermanent(color);
                    protectionFilter.add(new ColorPredicate(new ObjectColor(color)));
                }
            }
        }
        if (protectionFilter != null) {
            for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                perm.addAbility(new ProtectionAbility(protectionFilter), source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

}
