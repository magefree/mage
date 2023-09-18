package mage.cards.r;

import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RalStormConduit extends CardImpl {

    public RalStormConduit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAL);
        this.setStartingLoyalty(4);

        // Whenever you cast or copy an instant or sorcery spell, Ral, Storm Conduit deals 1 damage to target opponent or planeswalker.
        this.addAbility(new RalStormConduitTriggeredAbility());

        // +2: Scry 1.
        this.addAbility(new LoyaltyAbility(new ScryEffect(1, false), 2));

        // -2: When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()), -2));
    }

    private RalStormConduit(final RalStormConduit card) {
        super(card);
    }

    @Override
    public RalStormConduit copy() {
        return new RalStormConduit(this);
    }
}

class RalStormConduitTriggeredAbility extends TriggeredAbilityImpl {

    RalStormConduitTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), false);
        this.addTarget(new TargetOpponentOrPlaneswalker());
    }

    private RalStormConduitTriggeredAbility(final RalStormConduitTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case COPIED_STACKOBJECT:
            case SPELL_CAST:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        return spell != null && spell.isControlledBy(getControllerId()) && spell.isInstantOrSorcery(game);
    }

    @Override
    public RalStormConduitTriggeredAbility copy() {
        return new RalStormConduitTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you cast or copy an instant or sorcery spell, " +
                "{this} deals 1 damage to target opponent or planeswalker.";
    }
}
