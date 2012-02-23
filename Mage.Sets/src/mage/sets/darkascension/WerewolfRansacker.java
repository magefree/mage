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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward
 */
public class WerewolfRansacker extends CardImpl<WerewolfRansacker> {

    public WerewolfRansacker(UUID ownerId) {
        super(ownerId, 81, "Werewolf Ransacker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "");
        this.expansionSetCode = "DKA";
        this.subtype.add("Werewolf");
        
        // this card is the second face of double-faced card
        this.nightCard = true;
        this.canTransform = true;

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever this creature transforms into Werewolf Ransacker, you may destroy target artifact. If that artifact is put into a graveyard this way, Werewolf Ransacker deals 3 damage to that artifact's controller.
//        this.addAbility(new WerewolfRansackerAbility());
        
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Werewolf Ransacker.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), Constants.TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.getInstance(), TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public WerewolfRansacker(final WerewolfRansacker card) {
        super(card);
    }

    @Override
    public WerewolfRansacker copy() {
        return new WerewolfRansacker(this);
    }
}

//class WerewolfRansackerAbility extends TriggeredAbilityImpl<WerewolfRansackerAbility> {
//
//	private static final FilterPermanent filter = new FilterPermanent("artifact");
//
//	static {
//		filter.getCardType().add(CardType.ARTIFACT);
//		filter.setScopeCardType(Filter.ComparisonScope.Any);
//	}
//
//    public WerewolfRansackerAbility() {
//		super(Constants.Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
//		Target target = new TargetPermanent(filter);
//		target.setRequired(true);
//		this.addTarget(target);
//	}
//
//	public WerewolfRansackerAbility(final WerewolfRansackerAbility ability) {
//		super(ability);
//	}
//
//	@Override
//	public WerewolfRansackerAbility copy() {
//		return new WerewolfRansackerAbility(this);
//	}
//
//	@Override
//	public boolean checkTrigger(GameEvent event, Game game) {
//		if (event.getType() == GameEvent.EventType.TRANSFORMED) {
//            if (event.getTargetId().equals(sourceId)) {
//                return true;
//            }
//		}
//		return false;
//	}
//
//	@Override
//	public String getRule() {
//		return "Whenever this creature transforms into Werewolf Ransacker, you may destroy target artifact. If that artifact is put into a graveyard this way, Werewolf Ransacker deals 3 damage to that artifact's controller.";
//	}
//
//}
//
//class WerewolfRansackerEffect extends OneShotEffect<WerewolfRansackerEffect> {
//
//	public WerewolfRansackerEffect() {
//		super(Constants.Outcome.DestroyPermanent);
//	}
//
//	public WerewolfRansackerEffect(final WerewolfRansackerEffect effect) {
//		super(effect);
//	}
//
//	@Override
//	public WerewolfRansackerEffect copy() {
//		return new WerewolfRansackerEffect(this);
//	}
//
//	@Override
//	public boolean apply(Game game, Ability source) {
//        int affectedTargets = 0;
//		if (targetPointer.getTargets(source).size() > 0) {
//			for (UUID permanentId : targetPointer.getTargets(source)) {
//				Permanent permanent = game.getPermanent(permanentId);
//				if (permanent != null) {
//					if (permanent.destroy(source.getId(), game, false)) {
//                        Player player = game.getPlayer(permanent.getControllerId());
//                        if (player != null)
//                            player.damage(3, source.getSourceId(), game, false, true);
//                        affectedTargets++;
//                    }
//				}
//			}
//		}
//		return affectedTargets > 0;
//	}
//
//}
