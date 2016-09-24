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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author halljared
 */
public class AccursedWitch extends CardImpl {

    public AccursedWitch(UUID ownerId) {
        super(ownerId, 97, "Accursed Witch", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.canTransform = true;
        this.secondSideCard = new InfectiousCurse(ownerId);

        // Spells your opponents cast that target Accursed Witch cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AccursedWitchSpellsCostReductionEffect()));
        // When Accursed Witch dies, return it to the battlefield transformed under your control attached to target opponent.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesTriggeredAbility(new AccursedWitchReturnTransformedEffect()));
    }

    public AccursedWitch(final AccursedWitch card) {
        super(card);
    }

    @Override
    public AccursedWitch copy() {
        return new AccursedWitch(this);
    }
}

class AccursedWitchReturnTransformedEffect extends OneShotEffect {

    public AccursedWitchReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put {this} from your graveyard onto the battlefield transformed";
    }

    public AccursedWitchReturnTransformedEffect(final AccursedWitchReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public AccursedWitchReturnTransformedEffect copy() {
        return new AccursedWitchReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getState().getZone(source.getSourceId()).equals(Zone.GRAVEYARD)) {
                game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
                //note: should check for null after game.getCard
                Card card = game.getCard(source.getSourceId());
                if (card != null) {
                    card.putOntoBattlefield(game, Zone.BATTLEFIELD, source.getSourceId(), source.getControllerId(), false);
                }
            }
            return true;
        }
        return false;
    }
}

class AccursedWitchSpellsCostReductionEffect extends CostModificationEffectImpl {

    public AccursedWitchSpellsCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.REDUCE_COST);
        this.staticText = "Spells your opponents cast that target {this} cost {1} less to cast.";
    }

    protected AccursedWitchSpellsCostReductionEffect(AccursedWitchSpellsCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
                    Mode mode = abilityToModify.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        for (UUID targetUUID : target.getTargets()) {
                            Permanent permanent = game.getPermanent(targetUUID);
                            if (permanent != null && permanent.getId().equals(source.getSourceId())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public AccursedWitchSpellsCostReductionEffect copy() {
        return new AccursedWitchSpellsCostReductionEffect(this);
    }
}
