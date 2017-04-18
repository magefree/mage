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
package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AttachmentAttachedToCardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public class CrownOfTheAges extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Aura attached to a creature");

    static {
        filter.add(new AttachmentAttachedToCardTypePredicate(CardType.CREATURE));
        filter.add(new SubtypePredicate("Aura"));
    }

    public CrownOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {4}, {tap}: Attach target Aura attached to a creature to another creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrownOfTheAgesEffect(), new GenericManaCost(4));
        ability.addTarget(new TargetPermanent(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public CrownOfTheAges(final CrownOfTheAges card) {
        super(card);
    }

    @Override
    public CrownOfTheAges copy() {
        return new CrownOfTheAges(this);
    }
}

class CrownOfTheAgesEffect extends OneShotEffect {

    public CrownOfTheAgesEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Attach target Aura attached to a creature to another creature";
    }

    public CrownOfTheAgesEffect(final CrownOfTheAgesEffect effect) {
        super(effect);
    }

    @Override
    public CrownOfTheAgesEffect copy() {
        return new CrownOfTheAgesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID auraId = getTargetPointer().getFirst(game, source);
        Permanent aura = game.getPermanent(auraId);
        Permanent fromPermanent = game.getPermanent(aura.getAttachedTo());
        Player controller = game.getPlayer(source.getControllerId());
        if (fromPermanent != null && controller != null) {
            boolean passed = true;
            FilterCreaturePermanent filterChoice = new FilterCreaturePermanent("another creature");
            filterChoice.add(Predicates.not(new PermanentIdPredicate(fromPermanent.getId())));

            Target chosenCreatureToAttachAura = new TargetPermanent(filterChoice);
            chosenCreatureToAttachAura.setNotTarget(true);

            if (chosenCreatureToAttachAura.canChoose(source.getSourceId(), source.getControllerId(), game)
                    && controller.choose(Outcome.Neutral, chosenCreatureToAttachAura, source.getSourceId(), game)) {
                Permanent creatureToAttachAura = game.getPermanent(chosenCreatureToAttachAura.getFirstTarget());
                if (creatureToAttachAura != null) {
                    if (aura != null && passed) {
                        // Check the target filter
                        Target target = aura.getSpellAbility().getTargets().get(0);
                        if (target instanceof TargetPermanent) {
                            if (!target.getFilter().match(creatureToAttachAura, game)) {
                                passed = false;
                            }
                        }
                        // Check for protection
                        MageObject auraObject = game.getObject(auraId);
                        if (creatureToAttachAura.cantBeAttachedBy(auraObject, game)) {
                            passed = false;
                        }
                    }
                    if (passed) {
                        fromPermanent.removeAttachment(aura.getId(), game);
                        creatureToAttachAura.addAttachment(aura.getId(), game);
                        return true;
                    }
                }
            }
            return true;
        }

        return false;
    }
}
