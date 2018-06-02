
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElementalShamanToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public final class RebellionOfTheFlamekin extends CardImpl {

    public RebellionOfTheFlamekin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ENCHANTMENT},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        // Whenever you clash, you may pay {1}. If you do create a 3/1 Red Elemental Shaman creature token in play. If you won that token gains haste
        this.addAbility(new RebellionOfTheFlamekinTriggeredAbility());
    }

    public RebellionOfTheFlamekin(final RebellionOfTheFlamekin card) {
        super(card);
    }

    @Override
    public RebellionOfTheFlamekin copy() {
        return new RebellionOfTheFlamekin(this);
    }
}

class RebellionOfTheFlamekinTriggeredAbility extends TriggeredAbilityImpl {

    public RebellionOfTheFlamekinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RebellionOfTheFlamekinEffect());
    }

    public RebellionOfTheFlamekinTriggeredAbility(final RebellionOfTheFlamekinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RebellionOfTheFlamekinTriggeredAbility copy() {
        return new RebellionOfTheFlamekinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CLASHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean youWonTheClash = false;
         if (event.getData().equals("controller") && event.getPlayerId().equals(getControllerId())
                    || event.getData().equals("opponent") && event.getTargetId().equals(getControllerId())) {
            youWonTheClash = true;
        }
        for (Effect effect : getEffects()) {
            if (effect instanceof RebellionOfTheFlamekinEffect) {
                effect.setValue("clash", youWonTheClash);
            }
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you clash, you may pay {1}. If you do create a 3/1 Red Elemental Shaman creature token in play. If you won that token gains haste until end of turn";
    }
}

class RebellionOfTheFlamekinEffect extends OneShotEffect {

    public RebellionOfTheFlamekinEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public RebellionOfTheFlamekinEffect(final RebellionOfTheFlamekinEffect effect) {
        super(effect);
    }

    @Override
    public RebellionOfTheFlamekinEffect copy() {
        return new RebellionOfTheFlamekinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect createTokenEffect = new CreateTokenEffect(new ElementalShamanToken("LRW"));
            DoIfCostPaid doIfCostPaid = new DoIfCostPaid(createTokenEffect, new ManaCostsImpl("{1}"));
            doIfCostPaid.apply(game, source);
            Permanent token = game.getPermanent(createTokenEffect.getLastAddedTokenId());
            if (token != null && (boolean) (this.getValue("clash"))) {
                ContinuousEffect continuousEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                continuousEffect.setTargetPointer(new FixedTarget(createTokenEffect.getLastAddedTokenId()));
                game.addEffect(continuousEffect, source);
            }
            return true;

        }
        return false;

    }
}
