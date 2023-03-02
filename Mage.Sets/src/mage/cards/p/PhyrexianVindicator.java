package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

public class PhyrexianVindicator extends CardImpl {
    public PhyrexianVindicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{W}{W}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //If damage would be dealt to Phyrexian Vindicator, prevent that damage. When damage is prevented this way,
        //Phyrexian Vindicator deals that much damage to any other target.
        SimpleStaticAbility simpleStaticAbility = new SimpleStaticAbility(new PhyrexianVindicatorEffect());
        this.addAbility(simpleStaticAbility);
    }

    private PhyrexianVindicator(final PhyrexianVindicator card) {
        super(card);
    }

    @Override
    public PhyrexianVindicator copy() {
        return new PhyrexianVindicator(this);
    }
}

class PhyrexianVindicatorEffect extends ReplacementEffectImpl {

    public PhyrexianVindicatorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        staticText = "If damage would be dealt to {this}, prevent that damage. When damage is prevented this way, " +
                "{this} deals that much damage to any other target.";
    }

    private PhyrexianVindicatorEffect(final PhyrexianVindicatorEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianVindicatorEffect copy() {
        return new PhyrexianVindicatorEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        game.preventDamage(event, source, game, Integer.MAX_VALUE);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(damage), false,
                "{this} deals that much damage to any other target"
        );
        FilterCreaturePlayerOrPlaneswalker filter = new FilterCreaturePlayerOrPlaneswalker("any other target");
        filter.getPermanentFilter().add(AnotherPredicate.instance);
        ability.addTarget(new TargetAnyTarget(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }
}
