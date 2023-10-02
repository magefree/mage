package mage.cards.r;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.PitchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPlayer;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.PayResourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;

import java.util.UUID;

import javafx.scene.effect.Effect;

/**
 * @author ChesseTheWasp
 */
public final class RagingOnslaughtBlue extends CardImpl {

    public RagingOnslaughtBlue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{0}");

        // Ragefire deals 5 damage to target player.
        
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), (new PayResourceCost(3)));
        ability.addTarget(new TargetPlayer());

        this.addAbility(new ChannelAbility ("{0}", new PreventDamageToControllerEffect(Duration.EndOfTurn, 3)));
        this.addAbility(new PitchAbility ("{0}", new GetEnergyCountersControllerEffect(3)));

        this.addAbility(new OnEventTriggeredAbility(EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new SacrificeSourceEffect()));
    }

    private RagingOnslaughtBlue(final RagingOnslaughtBlue card) {
        super(card);
    }

    @Override
    public RagingOnslaughtBlue copy() {
        return new RagingOnslaughtBlue(this);
    }
}
