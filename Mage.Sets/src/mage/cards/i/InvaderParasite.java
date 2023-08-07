
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public final class InvaderParasite extends CardImpl {

    public InvaderParasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Imprint - When Invader Parasite enters the battlefield, exile target land.
        Ability ability = new EntersBattlefieldTriggeredAbility(new InvaderParasiteImprintEffect(), false);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // Whenever a land with the same name as the exiled card enters the battlefield under an opponent's control, Invader Parasite deals 2 damage to that player.
        this.addAbility(new InvaderParasiteTriggeredAbility());
    }

    private InvaderParasite(final InvaderParasite card) {
        super(card);
    }

    @Override
    public InvaderParasite copy() {
        return new InvaderParasite(this);
    }
}

class InvaderParasiteImprintEffect extends OneShotEffect {

    InvaderParasiteImprintEffect() {
        super(Outcome.Exile);
        staticText = "exile target land";
    }

    InvaderParasiteImprintEffect(final InvaderParasiteImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (sourcePermanent != null && targetPermanent != null) {
            targetPermanent.moveToExile(getId(), "Invader Parasite (Imprint)", source, game);
            sourcePermanent.imprint(targetPermanent.getId(), game);
        }
        return true;
    }

    @Override
    public InvaderParasiteImprintEffect copy() {
        return new InvaderParasiteImprintEffect(this);
    }
}

class InvaderParasiteTriggeredAbility extends TriggeredAbilityImpl {

    InvaderParasiteTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2));
    }

    InvaderParasiteTriggeredAbility(final InvaderParasiteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InvaderParasiteTriggeredAbility copy() {
        return new InvaderParasiteTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (targetPermanent != null && sourcePermanent != null) {
                if (!sourcePermanent.getImprinted().isEmpty()) {
                    Card imprintedCard = game.getCard(sourcePermanent.getImprinted().get(0));
                    if (imprintedCard != null && targetPermanent.getName().equals(imprintedCard.getName())) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land with the same name as the exiled card enters the battlefield under an opponent's control, {this} deals 2 damage to that player.";
    }
}
