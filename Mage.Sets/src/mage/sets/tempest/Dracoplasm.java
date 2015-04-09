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
package mage.sets.tempest;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class Dracoplasm extends CardImpl {

    public Dracoplasm(UUID ownerId) {
        super(ownerId, 341, "Dracoplasm", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{U}{R}");
        this.expansionSetCode = "TMP";
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // As Dracoplasm enters the battlefield, sacrifice any number of creatures. Dracoplasm's power becomes the total power of those creatures and its toughness becomes their total toughness.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DracoplasmEffect()));
        
        // {R}: Dracoplasm gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R)));
    }

    public Dracoplasm(final Dracoplasm card) {
        super(card);
    }

    @Override
    public Dracoplasm copy() {
        return new Dracoplasm(this);
    }
}

class DracoplasmEffect extends ReplacementEffectImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    static {
        filter.add(new AnotherPredicate());
    }
    
    public DracoplasmEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        this.staticText = "As {this} enters the battlefield, sacrifice any number of creatures. {this}'s power becomes the total power of those creatures and its toughness becomes their total toughness";
    }
    
    public DracoplasmEffect(final DracoplasmEffect effect) {
        super(effect);
    }
    
    @Override
    public DracoplasmEffect copy() {
        return new DracoplasmEffect(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());        

    }
    
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            Target target = new TargetControlledPermanent(0, Integer.MAX_VALUE, filter, true);
            if (!target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                return false;
            }
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            if (target.getTargets().size() > 0) {
                int power = 0;
                int toughness = 0;
                for (UUID targetId: target.getTargets()) {
                    Permanent targetCreature = game.getPermanent(targetId);
                    if (targetCreature != null && targetCreature.sacrifice(source.getSourceId(), game)) {
                        power += targetCreature.getPower().getValue();
                        toughness += targetCreature.getToughness().getValue();
                    }
                }
                ContinuousEffect effect = new SetPowerToughnessSourceEffect(power, toughness, Duration.Custom);
                game.addEffect(effect, source);
            }
        }
        return false;
    }
     
}
