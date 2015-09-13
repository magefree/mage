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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author fireshoes
 */
public class UlamogsNullifier extends CardImpl {

    public UlamogsNullifier(UUID ownerId) {
        super(ownerId, 207, "Ulamog's Nullifier", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Eldrazi");
        this.subtype.add("Processor");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ulamog's Nullifier enters the battlefield, you may put two cards your opponents own
        // from exile into their owners' graveyards. If you do, counter target spell.
        Effect effect = new UlamogsNullifierEffect();
        effect.setText("you may put two cards your opponents own from exile into their owners' graveyards. If you do, ");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, true);
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    public UlamogsNullifier(final UlamogsNullifier card) {
        super(card);
    }

    @Override
    public UlamogsNullifier copy() {
        return new UlamogsNullifier(this);
    }
}

class UlamogsNullifierEffect extends OneShotEffect {

    private final static FilterCard filter = new FilterCard("cards your opponents own from exile");

    static {
        filter.add(new OwnerPredicate(TargetController.OPPONENT));
    }

    public UlamogsNullifierEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may put two cards your opponents own from exile into their owners' graveyards. If you do, ";
    }

    public UlamogsNullifierEffect(final UlamogsNullifierEffect effect) {
        super(effect);
    }

    @Override
    public UlamogsNullifierEffect copy() {
        return new UlamogsNullifierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (controller != null && spell != null) {
            Target target = new TargetCardInExile(2, 2, filter, null);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Cards cardsToGraveyard = new CardsImpl(target.getTargets());
                    controller.moveCards(cardsToGraveyard, null, Zone.GRAVEYARD, source, game);
                    game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
                    return true;
                }
            }
        }
        return false;
    }
}
