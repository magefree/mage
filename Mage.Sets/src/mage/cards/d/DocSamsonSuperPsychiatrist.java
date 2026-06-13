package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.DynamicManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class DocSamsonSuperPsychiatrist extends CardImpl {

    public DocSamsonSuperPsychiatrist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.DOCTOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // If you would put one or more counters on a permanent you control, put that many plus one of each of those kinds of counters on that permanent instead.
        this.addAbility(new SimpleStaticAbility(new DocSamsonSuperPsychiatristEffect()));

        // {T}: Add X mana of any one color, where X is Doc Samson's power.
        this.addAbility(new DynamicManaAbility(
            Mana.AnyMana(1),
            SourcePermanentPowerValue.NOT_NEGATIVE,
            new TapSourceCost(),
            "Add X mana of any one color, where X is {this}'s power",
            true
        ));
    }

    private DocSamsonSuperPsychiatrist(final DocSamsonSuperPsychiatrist card) {
        super(card);
    }

    @Override
    public DocSamsonSuperPsychiatrist copy() {
        return new DocSamsonSuperPsychiatrist(this);
    }
}

class DocSamsonSuperPsychiatristEffect extends ReplacementEffectImpl {

    DocSamsonSuperPsychiatristEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "if you would put one or more counters on a permanent you control, " +
            "put that many plus one of each of those kinds of counters on that permanent instead";
    }

    private DocSamsonSuperPsychiatristEffect(final DocSamsonSuperPsychiatristEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(CardUtil.overflowInc(event.getAmount(), 1), true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getAmount() <= 0 || !source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        if (source.isControlledBy(event.getTargetId())) {
            return true;
        }
        Permanent permanent = game.getPermanentEntering(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanent(event.getTargetId());
        }
        return permanent != null && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public DocSamsonSuperPsychiatristEffect copy() {
        return new DocSamsonSuperPsychiatristEffect(this);
    }
}
