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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public class RidersOfGavony extends CardImpl {

    public RidersOfGavony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add("Human");
        this.subtype.add("Knight");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(VigilanceAbility.getInstance());

        // As Riders of Gavony enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Protect)));

        // Human creatures you control have protection from creatures of the chosen type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RidersOfGavonyGainAbilityControlledEffect()));
    }

    public RidersOfGavony(final RidersOfGavony card) {
        super(card);
    }

    @Override
    public RidersOfGavony copy() {
        return new RidersOfGavony(this);
    }
}

class RidersOfGavonyGainAbilityControlledEffect extends ContinuousEffectImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Human creatures you control");

    static {
        filter.add(new SubtypePredicate(SubType.HUMAN));
    }

    protected FilterPermanent protectionFilter;

    public RidersOfGavonyGainAbilityControlledEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Human creatures you control have protection from creatures of the chosen type";
    }

    public RidersOfGavonyGainAbilityControlledEffect(final RidersOfGavonyGainAbilityControlledEffect effect) {
        super(effect);
        protectionFilter = effect.protectionFilter;
    }

    @Override
    public RidersOfGavonyGainAbilityControlledEffect copy() {
        return new RidersOfGavonyGainAbilityControlledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (protectionFilter == null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                String subtype = (String) game.getState().getValue(permanent.getId() + "_type");
                if (subtype != null) {
                    protectionFilter = new FilterPermanent(subtype + 's');
                    protectionFilter.add(new SubtypePredicate(SubType.byDescription(subtype)));
                }
            }
        }
        if (protectionFilter != null) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                perm.addAbility(new ProtectionAbility(protectionFilter), source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

}
