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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class WarrenWeirding extends CardImpl<WarrenWeirding> {

    public WarrenWeirding(UUID ownerId) {
        super(ownerId, 104, "Warren Weirding", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{B}");
        this.expansionSetCode = "MMA";
        this.supertype.add("Tribal");
        this.subtype.add("Goblin");

        this.color.setBlack(true);

        // Target player sacrifices a creature. If a Goblin is sacrificed this way, that player puts two 1/1 black Goblin Rogue creature tokens onto the battlefield, and those tokens gain haste until end of turn.
        this.getSpellAbility().addEffect(new WarrenWeirdingEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public WarrenWeirding(final WarrenWeirding card) {
        super(card);
    }

    @Override
    public WarrenWeirding copy() {
        return new WarrenWeirding(this);
    }
}

class WarrenWeirdingEffect extends OneShotEffect<WarrenWeirdingEffect> {

    private static final FilterCreaturePermanent filterGoblin = new FilterCreaturePermanent();
    static {
        filterGoblin.add(new SubtypePredicate("Goblin"));
    }
    WarrenWeirdingEffect ( ) {
        super(Outcome.Sacrifice);
        staticText = "Target player sacrifices a creature. If a Goblin is sacrificed this way, that player puts two 1/1 black Goblin Rogue creature tokens onto the battlefield, and those tokens gain haste until end of turn";
    }

    WarrenWeirdingEffect ( WarrenWeirdingEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));

        FilterControlledPermanent filter = new FilterControlledPermanent("creature");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerIdPredicate(player.getId()));
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, false);
        target.setRequired(true);

        //A spell or ability could have removed the only legal target this player
        //had, if thats the case this ability should fizzle.
        if (target.canChoose(player.getId(), game)) {
            player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
                if (filterGoblin.match(permanent, game)) {
                    for (int i = 0; i < 2; i++) {
                        Token token = new WarrenWeirdingBlackGoblinRogueToken();
                        Effect effect = new CreateTokenTargetEffect(token);
                        effect.setTargetPointer(new FixedTarget(player.getId()));
                        if (effect.apply(game, source)) {
                            Permanent tokenPermanent = game.getPermanent(token.getLastAddedToken());
                            if (tokenPermanent != null) {
                                ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                                hasteEffect.setTargetPointer(new FixedTarget(tokenPermanent.getId()));
                                game.addEffect(hasteEffect, source);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WarrenWeirdingEffect copy() {
        return new WarrenWeirdingEffect(this);
    }

}

class WarrenWeirdingBlackGoblinRogueToken extends Token {
    WarrenWeirdingBlackGoblinRogueToken() {
        super("Goblin Rogue", "1/1 black Goblin Rogue creature tokens, and those tokens gain haste until end of turn");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Goblin");
        subtype.add("Rogue");
        power.setValue(1);
        toughness.setValue(1);
    }
}
