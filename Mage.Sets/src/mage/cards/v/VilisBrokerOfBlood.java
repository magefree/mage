package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VilisBrokerOfBlood extends CardImpl {

    public VilisBrokerOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {B}, Pay 2 life: Target creature gets -1/-1 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(-1, -1), new ManaCostsImpl("{B}")
        );
        ability.addCost(new PayLifeCost(2));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever you lose life, draw that many cards.
        this.addAbility(new VilisBrokerOfBloodTriggeredAbility());
    }

    private VilisBrokerOfBlood(final VilisBrokerOfBlood card) {
        super(card);
    }

    @Override
    public VilisBrokerOfBlood copy() {
        return new VilisBrokerOfBlood(this);
    }
}

class VilisBrokerOfBloodTriggeredAbility extends TriggeredAbilityImpl {

    VilisBrokerOfBloodTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private VilisBrokerOfBloodTriggeredAbility(final VilisBrokerOfBloodTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VilisBrokerOfBloodTriggeredAbility copy() {
        return new VilisBrokerOfBloodTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            this.getEffects().clear();
            this.addEffect(new DrawCardSourceControllerEffect(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you lose life, draw that many cards.";
    }
}
