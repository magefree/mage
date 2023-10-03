package mage.cards.j;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Jokulmorder extends CardImpl {

    public Jokulmorder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Jokulmorder enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Jokulmorder enters the battlefield, sacrifice it unless you sacrifice five lands.
        Effect effect = new SacrificeSourceUnlessPaysEffect(
                new SacrificeTargetCost(new TargetControlledPermanent(5, 5, new FilterControlledLandPermanent("five lands"), true)));
        effect.setText("sacrifice it unless you sacrifice five lands");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));

        // Jokulmorder doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));

        // Whenever you play an Island, you may untap Jokulmorder.
        this.addAbility(new JokulmorderTriggeredAbility());
    }

    private Jokulmorder(final Jokulmorder card) {
        super(card);
    }

    @Override
    public Jokulmorder copy() {
        return new Jokulmorder(this);
    }
}

class JokulmorderTriggeredAbility extends TriggeredAbilityImpl {

    JokulmorderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapSourceEffect(), true);
    }

    private JokulmorderTriggeredAbility(final JokulmorderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        return land != null
                && land.hasSubtype(SubType.ISLAND, game)
                && land.isControlledBy(this.controllerId);
    }

    @Override
    public JokulmorderTriggeredAbility copy() {
        return new JokulmorderTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you play an Island, you may untap {this}.";
    }
}
