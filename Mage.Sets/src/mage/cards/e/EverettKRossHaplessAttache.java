package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class EverettKRossHaplessAttache extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Commander creatures");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public EverettKRossHaplessAttache(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Commander creatures you control get +1/+1 and have lifelink.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter));
        Effect effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and have lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever an opponent attacks you with two or more creatures, draw a card.
        this.addAbility(new EverettKRossHaplessAttacheTriggeredAbility());
    }

    private EverettKRossHaplessAttache(final EverettKRossHaplessAttache card) {
        super(card);
    }

    @Override
    public EverettKRossHaplessAttache copy() {
        return new EverettKRossHaplessAttache(this);
    }
}

class EverettKRossHaplessAttacheTriggeredAbility extends TriggeredAbilityImpl {

    EverettKRossHaplessAttacheTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
    }

    private EverettKRossHaplessAttacheTriggeredAbility(final EverettKRossHaplessAttacheTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EverettKRossHaplessAttacheTriggeredAbility copy() {
        return new EverettKRossHaplessAttacheTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        if (controller == null || !game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            return false;
        }

        return game
                .getCombat()
                .getAttackers()
                .stream()
                .map(uuid -> game.getCombat().getDefendingPlayerId(uuid, game))
                .filter(getControllerId()::equals)
                .count() >= 2;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks you with two or more creatures, draw a card.";
    }
}
