package mage.cards.m;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KithkinSoldierToken;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class MilitiasPride extends CardImpl {

    public MilitiasPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.KITHKIN);

        // Whenever a creature you control attacks, you may pay {W}. If you do create a 1/1 white Kithkin Soldier creature token in play tapped and attacking
        this.addAbility(new MilitiasPrideTriggerAbility());

    }

    private MilitiasPride(final MilitiasPride card) {
        super(card);
    }

    @Override
    public MilitiasPride copy() {
        return new MilitiasPride(this);
    }
}

class MilitiasPrideTriggerAbility extends TriggeredAbilityImpl {

    public MilitiasPrideTriggerAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CreateTokenEffect(new KithkinSoldierToken(), 1, true, true), new ManaCostsImpl<>("{W}")));
    }

    public MilitiasPrideTriggerAbility(final MilitiasPrideTriggerAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        filter.add(TokenPredicate.FALSE);
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null && filter.match(permanent, controllerId, this, game);
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature you control attacks, you may pay {W}. If you do, create a 1/1 white Kithkin Soldier creature token that's tapped and attacking.";
    }

    @Override
    public MilitiasPrideTriggerAbility copy() {
        return new MilitiasPrideTriggerAbility(this);
    }
}
