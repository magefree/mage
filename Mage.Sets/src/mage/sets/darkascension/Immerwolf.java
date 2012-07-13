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
package mage.sets.darkascension;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class Immerwolf extends CardImpl<Immerwolf> {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("Wolf and Werewolf creatures");

    static {
        filter.add(Predicates.or(new SubtypePredicate("Wolf"), new SubtypePredicate("Werewolf")));
    }

    public Immerwolf(UUID ownerId) {
        super(ownerId, 141, "Immerwolf", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Wolf");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(IntimidateAbility.getInstance());

        // Other Wolf and Werewolf creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Constants.Duration.WhileOnBattlefield, filter, true)));

        // Non-Human Werewolves you control can't transform.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ImmerwolfEffect()));

    }

    public Immerwolf(final Immerwolf card) {
        super(card);
    }

    @Override
    public Immerwolf copy() {
        return new Immerwolf(this);
    }
}

class ImmerwolfEffect extends ReplacementEffectImpl<ImmerwolfEffect> {

    private final static FilterCreaturePermanent filterWerewolf = new FilterCreaturePermanent("Werewolf creature");
    private final static FilterCreaturePermanent filterNonhuman = new FilterCreaturePermanent("Non-human creature");

    static {
        filterWerewolf.add(new SubtypePredicate("Werewolf"));
        filterNonhuman.add(Predicates.not(new SubtypePredicate("Human")));
    }

    public ImmerwolfEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "Non-Human Werewolves you control can't transform";
    }

    public ImmerwolfEffect(final ImmerwolfEffect effect) {
        super(effect);
    }

    @Override
    public ImmerwolfEffect copy() {
        return new ImmerwolfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.TRANSFORM) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getControllerId().equals(source.getControllerId()) && filterWerewolf.match(permanent, game) && filterNonhuman.match(permanent, game)) {
                return true;
            }
        }
        return false;
    }
}
