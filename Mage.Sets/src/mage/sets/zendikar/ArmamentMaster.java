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
package mage.sets.zendikar;

import mage.Constants.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public class ArmamentMaster extends CardImpl<ArmamentMaster> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Other Kor creatures you control");

    static {
        filter.getSubtype().add("Kor");
    }

    public ArmamentMaster(UUID ownerId) {
        super(ownerId, 1, "Armament Master", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Kor");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArmamentMasterEffect()));
    }

    public ArmamentMaster(final ArmamentMaster card) {
        super(card);
    }

    @Override
    public ArmamentMaster copy() {
        return new ArmamentMaster(this);
    }
}

class ArmamentMasterEffect extends ContinuousEffectImpl<ArmamentMasterEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Other Kor creatures you control");

    static {
        filter.getSubtype().add("Kor");
    }

    public ArmamentMasterEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Other Kor creatures you control get +2/+2 for each Equipment attached to {this}";
    }

    public ArmamentMasterEffect(final ArmamentMasterEffect effect) {
        super(effect);
    }

    @Override
    public ArmamentMasterEffect copy() {
        return new ArmamentMasterEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game);
            for (Permanent perm : permanents) {
                if (!perm.getId().equals(source.getSourceId())) {
                    objects.add(perm.getId());
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = countEquipment(game, source);
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game);
        for (Permanent perm : permanents) {
            if (!this.affectedObjectsSet || objects.contains(perm.getId())) {
                if (!perm.getId().equals(source.getSourceId())) {
                    perm.addPower(2 * count);
                    perm.addToughness(2 * count);
                }
            }
        }
        return true;
    }

    private int countEquipment(Game game, Ability source) {
        int count = 0;
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            List<UUID> attachments = p.getAttachments();
            for (UUID attachmentId : attachments) {
                Permanent attached = game.getPermanent(attachmentId);
                if (attached != null && attached.getSubtype().contains("Equipment")) {
                    count++;
                }
            }

        }
        return count;
    }

}