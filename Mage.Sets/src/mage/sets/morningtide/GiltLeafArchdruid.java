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
package mage.sets.morningtide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class GiltLeafArchdruid extends CardImpl<GiltLeafArchdruid> {

    private static final FilterSpell filterSpell = new FilterSpell("a Druid spell");
    
    static {
        filterSpell.add(new SubtypePredicate("Druid"));
    }
    
    public GiltLeafArchdruid(UUID ownerId) {
        super(ownerId, 124, "Gilt-Leaf Archdruid", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a Druid spell, you may draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardControllerEffect(1), filterSpell, true));
        // Tap seven untapped Druids you control: Gain control of all lands target player controls.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainControlAllLandsEffect(Duration.EndOfGame), new TapTargetCost(new TargetControlledCreaturePermanent(7, 7, new FilterControlledCreaturePermanent("Druid", "Druids you control"), true)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public GiltLeafArchdruid(final GiltLeafArchdruid card) {
        super(card);
    }

    @Override
    public GiltLeafArchdruid copy() {
        return new GiltLeafArchdruid(this);
    }
}

class GainControlAllLandsEffect extends ContinuousEffectImpl<GainControlAllLandsEffect> {

    public GainControlAllLandsEffect(Duration duration) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
    }

    public GainControlAllLandsEffect(final GainControlAllLandsEffect effect) {
        super(effect);
    }

    @Override
    public GainControlAllLandsEffect copy() {
        return new GainControlAllLandsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (targetPointer != null) {
            for(Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), targetPointer.getFirst(game, source), game)){
                if (permanent != null) {
                    permanent.changeControllerId(source.getControllerId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Gain control of all lands target player controls";
    }
}
