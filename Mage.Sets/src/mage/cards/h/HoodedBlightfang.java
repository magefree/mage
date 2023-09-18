package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class HoodedBlightfang extends CardImpl {

    static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with deathtouch");

    static {
        filter.add(new AbilityPredicate(DeathtouchAbility.class));
    }

    public HoodedBlightfang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a creature you control with deathtouch attacks, each opponent loses 1 life and you gain 1 life.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false, filter
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // Whenever a creature you control with deathtouch deals damage to a planeswalker, destroy that planeswalker.
        this.addAbility(new HoodedBlightfangTriggeredAbility());
    }

    private HoodedBlightfang(final HoodedBlightfang card) {
        super(card);
    }

    @Override
    public HoodedBlightfang copy() {
        return new HoodedBlightfang(this);
    }
}

class HoodedBlightfangTriggeredAbility extends TriggeredAbilityImpl {

    HoodedBlightfangTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    private HoodedBlightfangTriggeredAbility(final HoodedBlightfangTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        Permanent damaged = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null
                || damaged == null
                || !StaticFilters.FILTER_PERMANENT_PLANESWALKER.match(damaged, game)
                || !HoodedBlightfang.filter.match(permanent, this.getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(damaged.getId(), damaged.getZoneChangeCounter(game)));
        return true;
    }

    @Override
    public HoodedBlightfangTriggeredAbility copy() {
        return new HoodedBlightfangTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control with deathtouch deals damage to a planeswalker, destroy that planeswalker.";
    }
}
