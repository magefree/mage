package mage.cards.a;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class AltarOfTheGoyf extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Lhurgoyf creatures");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(SubType.LHURGOYF.getPredicate());
    }

    public AltarOfTheGoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.LHURGOYF);

        // Whenever a creature you control attacks alone, it gets +X/+X until end of turn, where X is the number of card types among cards in all graveyard.
        this.addAbility(new AltarOfTheGoyfAbility().addHint(CardTypesInGraveyardCount.ALL));

        // Lhurgoyf creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    private AltarOfTheGoyf(final AltarOfTheGoyf card) {
        super(card);
    }

    @Override
    public AltarOfTheGoyf copy() {
        return new AltarOfTheGoyf(this);
    }
}

class AltarOfTheGoyfAbility extends TriggeredAbilityImpl{

    public AltarOfTheGoyfAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(
                CardTypesInGraveyardCount.instance, CardTypesInGraveyardCount.instance, Duration.EndOfTurn, true), false);
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
        if (game.isActivePlayer(this.controllerId)) {
            if (game.getCombat().attacksAlone()) {
                this.getEffects().setTargetPointer(new
                        FixedTarget(game.getCombat().getAttackers().get(0), game)); {
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks alone, " +
                "it gets +X/+X until end of turn, " +
                "where X is the number of card types among cards in all graveyard.";
    }
}
