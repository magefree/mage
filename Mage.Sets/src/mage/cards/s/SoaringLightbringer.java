package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.GlimmerToken;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoaringLightbringer extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("enchantment creatures");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public SoaringLightbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.GLIMMER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other enchantment creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever you attack a player, create a 1/1 white Glimmer enchantment creature token that's tapped and attacking that player.
        this.addAbility(new SoaringLightbringerTriggeredAbility());
    }

    private SoaringLightbringer(final SoaringLightbringer card) {
        super(card);
    }

    @Override
    public SoaringLightbringer copy() {
        return new SoaringLightbringer(this);
    }
}

class SoaringLightbringerTriggeredAbility extends TriggeredAbilityImpl {

    SoaringLightbringerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SoaringLightbringerEffect());
        setTriggerPhrase("Whenever you attack a player, ");
    }

    private SoaringLightbringerTriggeredAbility(final SoaringLightbringerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SoaringLightbringerTriggeredAbility copy() {
        return new SoaringLightbringerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId()) || game.getPlayer(event.getTargetId()) == null) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }
}

class SoaringLightbringerEffect extends OneShotEffect {

    SoaringLightbringerEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 white Glimmer enchantment creature token that's tapped and attacking that player";
    }

    private SoaringLightbringerEffect(final SoaringLightbringerEffect effect) {
        super(effect);
    }

    @Override
    public SoaringLightbringerEffect copy() {
        return new SoaringLightbringerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new GlimmerToken().putOntoBattlefield(
                1, game, source, source.getControllerId(), true, true,
                this.getTargetPointer().getFirst(game, source)
        );
    }
}
