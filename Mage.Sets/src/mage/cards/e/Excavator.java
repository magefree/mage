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
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.MountainwalkAbility;
import mage.abilities.keyword.PlainswalkAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class Excavator extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("basic land");
    static
    {
        filter.add(new SupertypePredicate("Basic"));
    }
   
    public Excavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}, Sacrifice a basic land: Target creature gains landwalk of each of the land types of the sacrificed land until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExcavatorEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public Excavator(final Excavator card) {
        super(card);
    }

    @Override
    public Excavator copy() {
        return new Excavator(this);
    }
}

class ExcavatorEffect extends ContinuousEffectImpl implements SourceEffect {

    private Abilities<Ability> abilities = new AbilitiesImpl();

    public ExcavatorEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        setText("Target creature gains landwalk of each of the land types of the sacrificed land until end of turn");
    }

    public ExcavatorEffect(final ExcavatorEffect effect) {
        super(effect);
        this.abilities = abilities.copy();
    }

    @Override
    public ExcavatorEffect copy() {
        return new ExcavatorEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for(Cost cost : source.getCosts()) {
            if(cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                for(Permanent permanent : sacrificeCost.getPermanents()) {
                    if(permanent.hasSubtype("Forest", game))
                    {
                        abilities.add(new ForestwalkAbility());
                    }
                    if(permanent.hasSubtype("Plains", game))
                    {
                        abilities.add(new PlainswalkAbility());
                    }
                    if(permanent.hasSubtype("Island", game))
                    {
                        abilities.add(new IslandwalkAbility());
                    }
                    if(permanent.hasSubtype("Mountain", game))
                    {
                        abilities.add(new MountainwalkAbility());
                    }
                    if(permanent.hasSubtype("Swamp", game))
                    {
                        abilities.add(new SwampwalkAbility());
                    }
                }
                
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
            if (permanent != null) {
                for(Ability ability : abilities)
                {
                    permanent.addAbility(ability, source.getSourceId(), game, false);
                }
            }
        }
        return true;
    }
}
