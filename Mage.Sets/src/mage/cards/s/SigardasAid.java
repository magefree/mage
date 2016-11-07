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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class SigardasAid extends CardImpl {

    private final static FilterArtifactPermanent filter = new FilterArtifactPermanent("an Equipment");
    private static final FilterCard filterCard = new FilterCard("Aura and Equipment spells");

    static {
        filter.add(new SubtypePredicate("Equipment"));
        filterCard.add(Predicates.or(new SubtypePredicate("Aura"), new SubtypePredicate("Equipment")));
    }

    public SigardasAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

        // You may cast Aura and Equipment spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filterCard, false)));

        // Whenever an Equipment enters the battlefield under your control, you may attach it to target creature you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new SigardasAidEffect(), filter, true, SetTargetPointer.PERMANENT, "");
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

    }

    public SigardasAid(final SigardasAid card) {
        super(card);
    }

    @Override
    public SigardasAid copy() {
        return new SigardasAid(this);
    }
}

class SigardasAidEffect extends OneShotEffect {

    public SigardasAidEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may attach it to target creature you control";
    }

    public SigardasAidEffect(final SigardasAidEffect effect) {
        super(effect);
    }

    @Override
    public SigardasAidEffect copy() {
        return new SigardasAidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent equipment = game.getPermanent(getTargetPointer().getFirst(game, source));
            Permanent targetCreature = game.getPermanent(source.getTargets().getFirstTarget());
            if (equipment != null && targetCreature != null) {
                targetCreature.addAttachment(equipment.getId(), game);
            }
            return true;
        }
        return false;
    }
}
