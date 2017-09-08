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
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public class StrataScythe extends CardImpl {

    public StrataScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StrataScytheImprintEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(SameNameAsExiledCountValue.getInstance(), SameNameAsExiledCountValue.getInstance())));
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));
    }

    public StrataScythe(final StrataScythe card) {
        super(card);
    }

    @Override
    public StrataScythe copy() {
        return new StrataScythe(this);
    }

}

class StrataScytheImprintEffect extends OneShotEffect {

    StrataScytheImprintEffect() {
        super(Outcome.Exile);
        staticText = "search your library for a land card, exile it, then shuffle your library";
    }

    StrataScytheImprintEffect(final StrataScytheImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(new FilterLandCard());
        if (player.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                UUID cardId = target.getTargets().get(0);
                Card card = player.getLibrary().remove(cardId, game);
                if (card != null) {
                    card.moveToExile(source.getSourceId(), "Strata Scythe", source.getSourceId(), game);
                    Permanent permanent = game.getPermanent(source.getSourceId());
                    if (permanent != null) {
                        permanent.imprint(card.getId(), game);
                    }
                }
            }
        }
        player.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public StrataScytheImprintEffect copy() {
        return new StrataScytheImprintEffect(this);
    }

}

class SameNameAsExiledCountValue implements DynamicValue {

    private static final SameNameAsExiledCountValue instance = new SameNameAsExiledCountValue();

    public static SameNameAsExiledCountValue getInstance() {
        return instance;
    }

    private SameNameAsExiledCountValue() {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int value = 0;
        Permanent permanent = game.getPermanent(sourceAbility.getSourceId());
        if (permanent != null && !permanent.getImprinted().isEmpty()) {
            FilterPermanent filterPermanent = new FilterPermanent();
            filterPermanent.add(new NamePredicate(game.getCard(permanent.getImprinted().get(0)).getName()));
            value = game.getBattlefield().count(filterPermanent, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
        }
        return value;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "land on the battlefield with the same name as the exiled card";
    }
}
