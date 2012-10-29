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
package mage.sets.betrayersofkamigawa;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class KitsunePalliator extends CardImpl<KitsunePalliator> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Samurai you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate("Samurai"));
    }

    public KitsunePalliator(UUID ownerId) {
        super(ownerId, 14, "Kitsune Palliator", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Fox");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {T}: Prevent the next 1 damage that would be dealt to each creature and each player this turn.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new KitsunePalliatorEffect(), new TapSourceCost()));
    }

    public KitsunePalliator(final KitsunePalliator card) {
        super(card);
    }

    @Override
    public KitsunePalliator copy() {
        return new KitsunePalliator(this);
    }
}

class KitsunePalliatorEffect extends OneShotEffect<KitsunePalliatorEffect> {

    public KitsunePalliatorEffect() {
        super(Outcome.PreventDamage);
        this.staticText = "Prevent the next 1 damage that would be dealt to each creature and each player this turn";
    }

    public KitsunePalliatorEffect(final KitsunePalliatorEffect effect) {
        super(effect);
    }

    @Override
    public KitsunePalliatorEffect copy() {
        return new KitsunePalliatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PreventDamageTargetEffect effect = new PreventDamageTargetEffect(Constants.Duration.EndOfTurn, 1);

        List<Permanent> permanents = game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game);
        for (Permanent permanent: permanents) {
            effect.setTargetPointer(new FixedTarget(permanent.getId()));
            game.addEffect(effect, source);
        }
        for (UUID playerId: game.getPlayer(source.getControllerId()).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                effect.setTargetPointer(new FixedTarget(player.getId()));
                game.addEffect(effect, source);
            }
        }
        return false;
    }
}
