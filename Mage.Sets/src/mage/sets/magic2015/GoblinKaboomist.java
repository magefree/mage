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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public class GoblinKaboomist extends CardImpl {

    public GoblinKaboomist(UUID ownerId) {
        super(ownerId, 144, "Goblin Kaboomist", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "M15";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, put a colorless artifact token named Land Mine onto the battlefield
        // with "{R}, Sacrifice this artifact: This artifact deals 2 damage to target attacking creature without flying."
        // Then flip a coin.  If you lose the flip, Goblin Kaboomist deals 2 damage to itself.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new LandMineToken()), TargetController.YOU, false);
        ability.addEffect(new GoblinKaboomistFlipCoinEffect());
        this.addAbility(ability);
    }

    public GoblinKaboomist(final GoblinKaboomist card) {
        super(card);
    }

    @Override
    public GoblinKaboomist copy() {
        return new GoblinKaboomist(this);
    }
}


class GoblinKaboomistFlipCoinEffect extends OneShotEffect {

    public GoblinKaboomistFlipCoinEffect() {
        super(Outcome.Damage);
    }
    
    public GoblinKaboomistFlipCoinEffect(final GoblinKaboomistFlipCoinEffect effect) {
        super(effect);
    }

    @Override
    public GoblinKaboomistFlipCoinEffect copy() {
        return new GoblinKaboomistFlipCoinEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            if (!player.flipCoin(game)) {
                String message = new StringBuilder(permanent.getLogName()).append(" deals 2 damage to itself").toString();
                game.informPlayers(message);
                permanent.damage(2, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
    
}

class LandMineToken extends Token {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature without flying");
    
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }
    
    public LandMineToken() {
        super("Land Mine", "colorless artifact token named Land Mine with \"{R}, Sacrifice this artifact: This artifact deals 2 damage to target attacking creature without flying.\"");
        this.setOriginalExpansionSetCode("M15");
        cardType.add(CardType.ARTIFACT);

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl("{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }
}
