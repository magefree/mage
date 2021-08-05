package mage.cards.a;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jmharmon
 */
public final class AltarOfTheGoyf extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent(SubType.LHURGOYF, "Lhurgoyf creatures");

    public AltarOfTheGoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.LHURGOYF);

        // Whenever a creature you control attacks alone, it gets +X/+X until end of turn, where X is the number of card types among cards in all graveyard.
        this.addAbility(new AltarOfTheGoyfAbility());

        // Lhurgoyf creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private AltarOfTheGoyf(final AltarOfTheGoyf card) {
        super(card);
    }

    @Override
    public AltarOfTheGoyf copy() {
        return new AltarOfTheGoyf(this);
    }
}

class AltarOfTheGoyfAbility extends TriggeredAbilityImpl {

    public AltarOfTheGoyfAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(
                CardTypesInGraveyardCount.ALL, CardTypesInGraveyardCount.ALL, Duration.EndOfTurn, true
        ), false);
        this.addHint(CardTypesInGraveyardHint.ALL);
    }

    public AltarOfTheGoyfAbility(final AltarOfTheGoyfAbility ability) {
        super(ability);
    }

    @Override
    public AltarOfTheGoyfAbility copy() {
        return new AltarOfTheGoyfAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(this.controllerId) && game.getCombat().attacksAlone()) {
            this.getEffects().setTargetPointer(new FixedTarget(game.getCombat().getAttackers().get(0), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks alone, " +
                "it gets +X/+X until end of turn, " +
                "where X is the number of card types among cards in all graveyards.";
    }
}
