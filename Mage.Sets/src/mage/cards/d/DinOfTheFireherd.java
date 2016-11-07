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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class DinOfTheFireherd extends CardImpl {

    public DinOfTheFireherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B/R}{B/R}{B/R}");

        // Create a 5/5 black and red Elemental creature token. Target opponent sacrifices a creature for each black creature you control, then sacrifices a land for each red creature you control.
        this.getSpellAbility().addEffect(new DinOfTheFireherdEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public DinOfTheFireherd(final DinOfTheFireherd card) {
        super(card);
    }

    @Override
    public DinOfTheFireherd copy() {
        return new DinOfTheFireherd(this);
    }
}

class DinOfTheFireherdEffect extends OneShotEffect {

    private final static FilterControlledCreaturePermanent blackCreatureFilter = new FilterControlledCreaturePermanent("black creatures you control");
    private final static FilterControlledCreaturePermanent redCreatureFilter = new FilterControlledCreaturePermanent("red creatures you control");

    static {
        blackCreatureFilter.add(new ColorPredicate(ObjectColor.BLACK));
        redCreatureFilter.add(new ColorPredicate(ObjectColor.RED));
    }

    public DinOfTheFireherdEffect() {
        super(Outcome.Neutral);
        this.staticText = "create a 5/5 black and red Elemental creature token. Target opponent sacrifices a creature for each black creature you control, then sacrifices a land for each red creature you control";
    }

    public DinOfTheFireherdEffect(final DinOfTheFireherdEffect effect) {
        super(effect);
    }

    @Override
    public DinOfTheFireherdEffect copy() {
        return new DinOfTheFireherdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied;
        Token token = new DinOfTheFireherdToken();
        applied = token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());

        int blackCreaturesControllerControls = game.getBattlefield().countAll(blackCreatureFilter, source.getControllerId(), game);
        int redCreaturesControllerControls = game.getBattlefield().countAll(redCreatureFilter, source.getControllerId(), game);

        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetOpponent != null) {
            Effect effect = new SacrificeEffect(new FilterControlledCreaturePermanent(), blackCreaturesControllerControls, "Target Opponent");
            effect.setTargetPointer(new FixedTarget(targetOpponent.getId()));
            effect.apply(game, source);

            Effect effect2 = new SacrificeEffect(new FilterControlledLandPermanent(), redCreaturesControllerControls, "Target Opponent");
            effect2.setTargetPointer(new FixedTarget(targetOpponent.getId()));
            effect2.apply(game, source);
            applied = true;
        }
        return applied;
    }
}

class DinOfTheFireherdToken extends Token {

    public DinOfTheFireherdToken() {
        super("", "5/5 black and red Elemental creature");
        cardType.add(CardType.CREATURE);
        subtype.add("Elemental");
        color.setBlack(true);
        color.setRed(true);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }
}
