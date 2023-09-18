package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrCarahTheBold extends CardImpl {

    public SyrCarahTheBold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Syr Carah, the Bold or an instant or sorcery spell you control deals damage to a player, exile the top card of your library. You may play that card this turn.
        this.addAbility(new SyrCarahTheBoldTriggeredAbility());

        // {T}: Syr Carah deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SyrCarahTheBold(final SyrCarahTheBold card) {
        super(card);
    }

    @Override
    public SyrCarahTheBold copy() {
        return new SyrCarahTheBold(this);
    }
}

class SyrCarahTheBoldTriggeredAbility extends TriggeredAbilityImpl {

    SyrCarahTheBoldTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTopXMayPlayUntilEndOfTurnEffect(1), false);
    }

    private SyrCarahTheBoldTriggeredAbility(final SyrCarahTheBoldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SyrCarahTheBoldTriggeredAbility copy() {
        return new SyrCarahTheBoldTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        Spell spell = game.getSpellOrLKIStack(event.getSourceId());
        return spell != null && spell.isInstantOrSorcery(game)
                && spell.isControlledBy(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} or an instant or sorcery spell you control deals damage to a player, " +
                "exile the top card of your library. You may play that card this turn.";
    }
}
