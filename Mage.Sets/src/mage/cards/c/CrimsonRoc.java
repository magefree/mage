package mage.cards.c;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrimsonRoc extends CardImpl {

    public CrimsonRoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Crimson Roc blocks a creature without flying, Crimson Roc gets +1/+0 and gains first strike until end of turn.
        this.addAbility(new CrimsonRocTriggeredAbility());
    }

    private CrimsonRoc(final CrimsonRoc card) {
        super(card);
    }

    @Override
    public CrimsonRoc copy() {
        return new CrimsonRoc(this);
    }
}

class CrimsonRocTriggeredAbility extends TriggeredAbilityImpl {

    CrimsonRocTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), false);
        this.addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn));
    }

    private CrimsonRocTriggeredAbility(final CrimsonRocTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && permanent.isCreature(game)
                && !permanent.hasAbility(FlyingAbility.getInstance(), game);
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks a creature without flying, " +
                "{this} gets +1/+0 and gains first strike until end of turn.";
    }

    @Override
    public CrimsonRocTriggeredAbility copy() {
        return new CrimsonRocTriggeredAbility(this);
    }
}
