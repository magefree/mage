
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author tcontis
 */
public final class TrainedPronghorn extends CardImpl {

    public TrainedPronghorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.ANTELOPE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.toughness = new MageInt(1);

        //Discard a card: Prevent all damage that would be dealt to Trained Pronghorn this turn
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventAllDamageToSourceEffect(Duration.EndOfTurn), new DiscardCardCost()));

    }

    public TrainedPronghorn(final TrainedPronghorn card) {
        super(card);
    }

    @Override
    public TrainedPronghorn copy() {
        return new TrainedPronghorn(this);
    }
}


