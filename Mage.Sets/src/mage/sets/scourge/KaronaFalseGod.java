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
package mage.sets.scourge;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class KaronaFalseGod extends CardImpl {

    public KaronaFalseGod(UUID ownerId) {
        super(ownerId, 138, "Karona, False God", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}{R}{G}");
        this.expansionSetCode = "SCG";
        this.supertype.add("Legendary");
        this.subtype.add("Avatar");

        this.color.setGreen(true);
        this.color.setBlue(true);
        this.color.setWhite(true);
        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // At the beginning of each player's upkeep, that player untaps Karona, False God and gains control of it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new KaronaFalseGodControlEffect(), TargetController.ANY, false));
        
        // Whenever Karona attacks, creatures of the creature type of your choice get +3/+3 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new KaronaFalseGodBoostEffect(), false, "Whenever {this} attacks, creatures of the creature type of your choice get +3/+3 until end of turn."));
    }

    public KaronaFalseGod(final KaronaFalseGod card) {
        super(card);
    }

    @Override
    public KaronaFalseGod copy() {
        return new KaronaFalseGod(this);
    }
}

class KaronaFalseGodControlEffect extends ContinuousEffectImpl {
    
    KaronaFalseGodControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "that player untaps {this} and gains control of it.";
    }
    
    KaronaFalseGodControlEffect(final KaronaFalseGodControlEffect effect) {
        super(effect);
    }
    
    @Override
    public KaronaFalseGodControlEffect copy() {
        return new KaronaFalseGodControlEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null && permanent != null) {
            permanent.untap(game);
            if (permanent.changeControllerId(player.getId(), game)) {
                return true;
            }
        }
        return false;
    }
}

class KaronaFalseGodBoostEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private String typeChosen = "";

    KaronaFalseGodBoostEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "creatures of the creature type of your choice get +3/+3 until end of turn.";
    }

    KaronaFalseGodBoostEffect(final KaronaFalseGodBoostEffect effect) {
        super(effect);
    }

    @Override
    public KaronaFalseGodBoostEffect copy() {
        return new KaronaFalseGodBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (typeChosen.isEmpty()) {
                Choice typeChoice = new ChoiceImpl(true);
                typeChoice.setMessage("Choose creature type");
                typeChoice.setChoices(CardRepository.instance.getCreatureTypes());
                while (!player.choose(Outcome.BoostCreature, typeChoice, game)) {
                    if (!player.isInGame()) {
                        return false;
                    }
                }
                typeChosen = typeChoice.getChoice();
                game.informPlayers(player.getName() + " has chosen " + typeChosen);
            }
            for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (perm.hasSubtype(typeChosen)) {
                    perm.addPower(3);
                    perm.addToughness(3);
                }
            }
        }
        return true;
    }
}