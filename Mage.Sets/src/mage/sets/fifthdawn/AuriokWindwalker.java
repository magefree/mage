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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public class AuriokWindwalker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Equipment you control");
    static {
        filter.add(new SubtypePredicate("Equipment"));
    }

    public AuriokWindwalker(UUID ownerId) {
        super(ownerId, 5, "Auriok Windwalker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "5DN";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {tap}: Attach target Equipment you control to target creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AttachTargetEquipmentEffect(), new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent(filter));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public AuriokWindwalker(final AuriokWindwalker card) {
        super(card);
    }

    @Override
    public AuriokWindwalker copy() {
        return new AuriokWindwalker(this);
    }
}


class AttachTargetEquipmentEffect extends OneShotEffect {

    public AttachTargetEquipmentEffect() {
        super(Outcome.BoostCreature);
        staticText = "Attach target Equipment you control to target creature you control";
    }

    public AttachTargetEquipmentEffect(final AttachTargetEquipmentEffect effect) {
        super(effect);
    }

    @Override
    public AttachTargetEquipmentEffect copy() {
        return new AttachTargetEquipmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getFirstTarget());
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null && equipment != null) {
            return creature.addAttachment(equipment.getId(), game);
        }
        return false;
    }
}
