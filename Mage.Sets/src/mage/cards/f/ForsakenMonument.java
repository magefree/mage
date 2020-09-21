package mage.cards.f;

import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForsakenMonument extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("colorless creatures");
    private static final FilterSpell filter2 = new FilterSpell("a colorless spell");

    static {
        filter.add(ColorlessPredicate.instance);
        filter2.add(ColorlessPredicate.instance);
    }

    public ForsakenMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.addSuperType(SuperType.LEGENDARY);

        // Colorless creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, filter
        )));

        // Whenever you tap a permanent for {C}, add an additional {C}.
        this.addAbility(new ForsakenMonumentTriggeredManaAbility());

        // Whenever you cast a colorless spell, you gain 2 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(2), filter2, false));
    }

    private ForsakenMonument(final ForsakenMonument card) {
        super(card);
    }

    @Override
    public ForsakenMonument copy() {
        return new ForsakenMonument(this);
    }
}

class ForsakenMonumentTriggeredManaAbility extends TriggeredManaAbility {

    ForsakenMonumentTriggeredManaAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.ColorlessMana(1)));
    }

    private ForsakenMonumentTriggeredManaAbility(final ForsakenMonumentTriggeredManaAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        ManaEvent mEvent = (ManaEvent) event;
        if (permanent == null || !mEvent.getPlayerId().equals(getControllerId())) {
            return false;
        }
        return mEvent.getMana().getColorless() > 0;
    }

    @Override
    public ForsakenMonumentTriggeredManaAbility copy() {
        return new ForsakenMonumentTriggeredManaAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a permanent for {C}, add an additional {C}.";
    }
}
