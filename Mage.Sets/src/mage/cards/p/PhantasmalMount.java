package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class PhantasmalMount extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("with toughness 2 or less");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public PhantasmalMount(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {tap}: Target creature you control with toughness 2 or less gets +1/+1 and gains flying until end of turn. When Phantasmal Mount leaves the battlefield this turn, sacrifice that creature. When the creature leaves the battlefield this turn, sacrifice Phantasmal Mount.
        Ability activatedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhantasmalMountEffect(), new TapSourceCost());
        activatedAbility.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(activatedAbility);

    }

    private PhantasmalMount(final PhantasmalMount card) {
        super(card);
    }

    @Override
    public PhantasmalMount copy() {
        return new PhantasmalMount(this);
    }
}

class PhantasmalMountEffect extends OneShotEffect {

    PhantasmalMountEffect() {
        super(Outcome.Neutral);
        staticText = "Target creature you control with toughness 2 or less gets +1/+1 "
                + "and gains flying until end of turn. When {this} leaves the battlefield this turn, "
                + "sacrifice that creature. When the creature leaves the battlefield this turn, sacrifice {this}";
    }

    PhantasmalMountEffect(PhantasmalMountEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            ContinuousEffect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
            game.addEffect(effect, source);
            Effect sacrificeCreatureEffect = new SacrificeTargetEffect();
            Effect sacrificePhantasmalMountEffect = new SacrificeTargetEffect();
            ContinuousEffect gainAbility = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
            gainAbility.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
            game.addEffect(gainAbility, source);
            sacrificeCreatureEffect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
            sacrificePhantasmalMountEffect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
            DelayedTriggeredAbility dTA = new PhantasmalMountDelayedTriggeredAbility(
                    sacrificeCreatureEffect,
                    source.getSourceId());
            DelayedTriggeredAbility dTA2 = new PhantasmalMountDelayedTriggeredAbility(
                    sacrificePhantasmalMountEffect,
                    source.getFirstTarget());
            game.addDelayedTriggeredAbility(dTA, source);
            game.addDelayedTriggeredAbility(dTA2, source);
            return true;
        }
        return false;
    }

    @Override
    public PhantasmalMountEffect copy() {
        return new PhantasmalMountEffect(this);
    }
}

class PhantasmalMountDelayedTriggeredAbility extends DelayedTriggeredAbility {

    UUID creatureId;

    PhantasmalMountDelayedTriggeredAbility(Effect effect, UUID creatureId) {
        super(effect, Duration.EndOfTurn, true);
        this.creatureId = creatureId;
    }

    PhantasmalMountDelayedTriggeredAbility(PhantasmalMountDelayedTriggeredAbility ability) {
        super(ability);
        this.creatureId = ability.creatureId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && event.getTargetId().equals(creatureId)) {
            return true;
        }
        return false;
    }

    @Override
    public PhantasmalMountDelayedTriggeredAbility copy() {
        return new PhantasmalMountDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "this left the battlefield";
    }
}
