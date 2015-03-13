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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class DragonTempest extends CardImpl {

    private static final FilterCreaturePermanent filterFlying = new FilterCreaturePermanent("a creature with flying");

    static {
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
    }

    public DragonTempest(UUID ownerId) {
        super(ownerId, 136, "Dragon Tempest", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.expansionSetCode = "DTK";

        // Whenever a creature with flying enters the battlefield under your control, it gains haste until the end of turn.
        Effect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("it gains haste until the end of turn");
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, effect, filterFlying, false, SetTargetPointer.PERMANENT, ""));

        // Whenever a Dragon enters the battlefield under your control, it deals X damage to target creature or player, where X is the number of Dragons you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new DragonTempestDamageEffect(),
                new FilterCreaturePermanent("Dragon", "a Dragon"),
                false,
                SetTargetPointer.PERMANENT,
                ""
        );
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

    }

    public DragonTempest(final DragonTempest card) {
        super(card);
    }

    @Override
    public DragonTempest copy() {
        return new DragonTempest(this);
    }
}

class DragonTempestDamageEffect extends OneShotEffect {

    private static final FilterControlledPermanent dragonFilter = new FilterControlledPermanent();

    static {
        dragonFilter.add(new SubtypePredicate("Dragon"));
    }

    public DragonTempestDamageEffect() {
        super(Outcome.Damage);
        staticText = "it deals X damage to target creature or player, where X is the number of Dragons you control";
    }

    public DragonTempestDamageEffect(final DragonTempestDamageEffect effect) {
        super(effect);
    }

    @Override
    public DragonTempestDamageEffect copy() {
        return new DragonTempestDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = game.getBattlefield().countAll(dragonFilter, controller.getId(), game);
            if (amount > 0) {
                Permanent targetCreature = game.getPermanent(source.getTargets().getFirstTarget());
                if (targetCreature != null) {
                    targetCreature.damage(amount, getTargetPointer().getFirst(game, source), game, false, true);
                } else {
                    Player player = game.getPlayer(source.getTargets().getFirstTarget());
                    if (player != null) {
                        player.damage(amount, getTargetPointer().getFirst(game, source), game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
