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
package mage.sets.avacynrestored;

import java.util.LinkedList;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class EatenBySpiders extends CardImpl<EatenBySpiders> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.getAbilities().add(FlyingAbility.getInstance());
    }

    public EatenBySpiders(UUID ownerId) {
        super(ownerId, 177, "Eaten by Spiders", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "AVR";

        this.color.setGreen(true);

        // Destroy target creature with flying and all Equipment attached to that creature.
        this.getSpellAbility().addEffect(new EatenBySpidersEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public EatenBySpiders(final EatenBySpiders card) {
        super(card);
    }

    @Override
    public EatenBySpiders copy() {
        return new EatenBySpiders(this);
    }
}

class EatenBySpidersEffect extends OneShotEffect<EatenBySpidersEffect> {

    public EatenBySpidersEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature with flying and all Equipment attached to that creature";
    }

    public EatenBySpidersEffect(final EatenBySpidersEffect effect) {
        super(effect);
    }

    @Override
    public EatenBySpidersEffect copy() {
        return new EatenBySpidersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            LinkedList<UUID> attachments = new LinkedList<UUID>();
            attachments.addAll(permanent.getAttachments());

            for (UUID attachmentId : attachments) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment.hasSubtype("Equipment")) {
                    attachment.destroy(source.getId(), game, false);
                }
            }

            permanent.destroy(source.getId(), game, false);
            return true;
        }
        return false;
    }
}
