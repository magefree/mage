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
package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public class AuriokSurvivors extends CardImpl<AuriokSurvivors> {
    private static final FilterCard filter = new FilterCard("Equipment card from your graveyard");

    static {
        filter.getSubtype().add("Equipment");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public AuriokSurvivors(UUID ownerId) {
        super(ownerId, 3, "Auriok Survivors", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // When Auriok Survivors enters the battlefield, you may return target Equipment card from your graveyard to the battlefield. If you do, you may attach it to Auriok Survivors.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        ability.addEffect(new AuriokSurvivorsEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public AuriokSurvivors(final AuriokSurvivors card) {
        super(card);
    }

    @Override
    public AuriokSurvivors copy() {
        return new AuriokSurvivors(this);
    }
}

class AuriokSurvivorsEffect extends OneShotEffect<AuriokSurvivorsEffect> {
    AuriokSurvivorsEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "If you do, you may attach it to {this}";
    }

    AuriokSurvivorsEffect(final AuriokSurvivorsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (p != null && player != null && sourcePermanent != null) {
            if (player.chooseUse(Constants.Outcome.Benefit, "Attach " + p.getName() + " to " + sourcePermanent.getName() + "?", game)) {
                sourcePermanent.addAttachment(p.getId(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public AuriokSurvivorsEffect copy() {
        return new AuriokSurvivorsEffect(this);
    }
}
