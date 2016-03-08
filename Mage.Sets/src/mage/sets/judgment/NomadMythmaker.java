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
package mage.sets.judgment;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class NomadMythmaker extends CardImpl {

    private static final FilterCard FILTER = new FilterCard("Aura card");

    static {
        FILTER.add(new CardTypePredicate(CardType.ENCHANTMENT));
        FILTER.add(new SubtypePredicate("Aura"));
    }

    public NomadMythmaker(UUID ownerId) {
        super(ownerId, 15, "Nomad Mythmaker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "JUD";
        this.subtype.add("Human");
        this.subtype.add("Nomad");
        this.subtype.add("Cleric");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, {tap}: Put target Aura card from a graveyard onto the battlefield under your control attached to a creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NomadMythmakerEffect(), new ManaCostsImpl("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(1, FILTER));
        this.addAbility(ability);

    }

    public NomadMythmaker(final NomadMythmaker card) {
        super(card);
    }

    @Override
    public NomadMythmaker copy() {
        return new NomadMythmaker(this);
    }
}

class NomadMythmakerEffect extends OneShotEffect {

    public NomadMythmakerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put target Aura card from a graveyard onto the battlefield under your control attached to a creature you control.";
    }

    public NomadMythmakerEffect(final NomadMythmakerEffect effect) {
        super(effect);
    }

    @Override
    public NomadMythmakerEffect copy() {
        return new NomadMythmakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card aura = game.getCard(source.getFirstTarget());
        if (controller == null
                || aura == null) {
            return false;
        }
        FilterControlledCreaturePermanent FILTER = new FilterControlledCreaturePermanent("Aura card in a graveyard");
        TargetControlledPermanent target = new TargetControlledPermanent(FILTER);
        target.setNotTarget(true);
        if (target.canChoose(source.getControllerId(), game)
                && controller.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null
                    && !permanent.cantBeEnchantedBy(aura, game)) {
                game.getState().setValue("attachTo:" + aura.getId(), permanent);
                controller.moveCards(aura, Zone.BATTLEFIELD, source, game);
                return permanent.addAttachment(aura.getId(), game);
            }
        }
        return false;
    }
}
