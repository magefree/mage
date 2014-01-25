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
package mage.sets.bornofthegods;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterMana;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ChampionOfStraySouls extends CardImpl<ChampionOfStraySouls> {

    public ChampionOfStraySouls(UUID ownerId) {
        super(ownerId, 63, "Champion of Stray Souls", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Skeleton");
        this.subtype.add("Warrior");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        /**
         *  You choose the targets of the first ability as you activate that ability,
         *  before you pay any costs. You can't target any of the creatures you sacrifice.
         */
        // {3}{B}{B}, {T}, Sacrifice X other creatures: Return X target creatures from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Return X target creatures from your graveyard to the battlefield");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{3}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ChampionOfStraySoulsSacrificeCost());
        ability.addTarget(new TargetCardInYourGraveyard(0,Integer.MAX_VALUE, new FilterCreatureCard()));
        this.addAbility(ability);

        // {5}{B}{B}: Put Champion of Stray Souls on top of your library from your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, 
                new PutOnLibrarySourceEffect(true, "Put {this} on top of your library from your graveyard"),
                new ManaCostsImpl("{5}{B}{B}")));

    }

    public ChampionOfStraySouls(final ChampionOfStraySouls card) {
        super(card);
    }

    @Override
    public ChampionOfStraySouls copy() {
        return new ChampionOfStraySouls(this);
    }
}

class ChampionOfStraySoulsSacrificeCost extends CostImpl<ChampionOfStraySoulsSacrificeCost> implements VariableCost {

    protected int amountPaid = 0;

    public ChampionOfStraySoulsSacrificeCost() {
        this.text = "Sacrifice X other creatures";
    }

    public ChampionOfStraySoulsSacrificeCost(final ChampionOfStraySoulsSacrificeCost cost) {
        super(cost);
        this.amountPaid = cost.amountPaid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        amountPaid = 0;        
        int creaturesToSacrifice = ability.getTargets().get(0).getTargets().size();
        this.text = new StringBuilder(creaturesToSacrifice).append(" other creatures you control").toString();
        
        FilterCreaturePermanent filter = new FilterCreaturePermanent(this.text);
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
        TargetCreaturePermanent target = new TargetCreaturePermanent(creaturesToSacrifice, creaturesToSacrifice, filter, true);
        
        if (target.canChoose(controllerId, game) && target.choose(Outcome.Sacrifice, controllerId, sourceId, game)) {
            for (UUID creatureId: (List<UUID>) target.getTargets()) {
                Permanent creature = game.getPermanent(creatureId);
                if (creature != null) {
                    creature.sacrifice(sourceId, game);
                    amountPaid++;
                }

            }
        } else {
            return false;
        }
        paid = true;
        return true;
    }

    @Override
    public int getAmount() {
        return amountPaid;
    }

    @Override
    public void setFilter(FilterMana filter) {
    }

    @Override
    public FilterMana getFilter() {
        return new FilterMana();
    }

    @Override
    public ChampionOfStraySoulsSacrificeCost copy() {
        return new ChampionOfStraySoulsSacrificeCost(this);
    }

    @Override
    public void setAmount(int amount) {
        amountPaid = amount;
    }
}

