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
package mage.sets.timespiral;

import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class GauntletOfPower extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a basic land");

    static {
        filter.add(new SupertypePredicate("Basic"));
    }

    public GauntletOfPower(UUID ownerId) {
        super(ownerId, 255, "Gauntlet of Power", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "TSP";

        // As Gauntlet of Power enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));
        // Creatures of the chosen color get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GauntletOfPowerEffect1()));

        // Whenever a basic land is tapped for mana of the chosen color, its controller adds one mana of that color to his or her mana pool.
        this.addAbility(new TapForManaAllTriggeredAbility(new GauntletOfPowerEffectEffect2(), filter, SetTargetPointer.PERMANENT));
    }

    public GauntletOfPower(final GauntletOfPower card) {
        super(card);
    }

    @Override
    public GauntletOfPower copy() {
        return new GauntletOfPower(this);
    }
}

class GauntletOfPowerEffect1 extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public GauntletOfPowerEffect1() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures of the chosen color get +1/+1";
    }

    public GauntletOfPowerEffect1(final GauntletOfPowerEffect1 effect) {
        super(effect);
    }

    @Override
    public GauntletOfPowerEffect1 copy() {
        return new GauntletOfPowerEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (color != null) {
            for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (perm.getColor(game).contains(color)) {
                    perm.addPower(1);
                    perm.addToughness(1);
                }
            }
        }
        return true;
    }

}

class TapForManaAllTriggeredAbility extends TriggeredManaAbility {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;

    public TapForManaAllTriggeredAbility(ManaEffect effect, FilterPermanent filter, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, false);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public TapForManaAllTriggeredAbility(TapForManaAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
            ManaEvent mEvent = (ManaEvent) event;
            ObjectColor color = (ObjectColor) game.getState().getValue(getSourceId() + "_color");
            if (color != null) {
                Mana mana = mEvent.getMana();
                boolean colorFits = false;
                if (color.isBlack() && mana.getBlack() > 0) {
                    colorFits = true;
                } else if (color.isBlue() && mana.getBlue() > 0) {
                    colorFits = true;
                } else if (color.isGreen() && mana.getGreen() > 0) {
                    colorFits = true;
                } else if (color.isWhite() && mana.getWhite() > 0) {
                    colorFits = true;
                } else if (color.isRed() && mana.getRed() > 0) {
                    colorFits = true;
                }
                if (colorFits) {

                    for (Effect effect : getEffects()) {
                        effect.setValue("mana", mEvent.getMana());
                    }
                    switch (setTargetPointer) {
                        case PERMANENT:
                            getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
                            break;
                        case PLAYER:
                            getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getControllerId()));
                            break;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TapForManaAllTriggeredAbility copy() {
        return new TapForManaAllTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " for mana, " + super.getRule();
    }
}

class GauntletOfPowerEffectEffect2 extends ManaEffect {

    public GauntletOfPowerEffectEffect2() {
        super();
        staticText = "its controller adds one mana of that color to his or her mana pool";
    }

    public GauntletOfPowerEffectEffect2(final GauntletOfPowerEffectEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if(land != null){
            Player player = game.getPlayer(land.getControllerId());
            Mana mana = (Mana) getValue("mana");
            if (player != null && mana != null) {
                player.getManaPool().addMana(mana, game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

    @Override
    public GauntletOfPowerEffectEffect2 copy() {
        return new GauntletOfPowerEffectEffect2(this);
    }
}
