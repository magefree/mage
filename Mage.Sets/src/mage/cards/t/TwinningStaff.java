package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinningStaff extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("instant or sorcery spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public TwinningStaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // If you would copy a spell one or more times, instead copy it that many times plus an additional time. You may choose new targets for the additional copy.
        this.addAbility(new SimpleStaticAbility(new TwinningStaffEffect()));

        // {7}, {T}: Copy target instant or sorcery spell you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetSpellEffect(), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private TwinningStaff(final TwinningStaff card) {
        super(card);
    }

    @Override
    public TwinningStaff copy() {
        return new TwinningStaff(this);
    }
}

class TwinningStaffEffect extends ReplacementEffectImpl {

    TwinningStaffEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would copy a spell one or more times, " +
                "instead copy it that many times plus an additional time. " +
                "You may choose new targets for the additional copy.";
    }

    private TwinningStaffEffect(final TwinningStaffEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COPY_STACKOBJECT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId())
                && game.getSpellOrLKIStack(event.getTargetId()) != null
                && event.getAmount() >= 1;
    }

    @Override
    public TwinningStaffEffect copy() {
        return new TwinningStaffEffect(this);
    }
}