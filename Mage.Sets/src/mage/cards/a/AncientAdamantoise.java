package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientAdamantoise extends CardImpl {

    public AncientAdamantoise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}{G}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(8);
        this.toughness = new MageInt(20);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}")));

        // Damage isn't removed from this creature during cleanup steps.
        this.addAbility(new SimpleStaticAbility(new AncientAdamantoiseDamageEffect()));

        // All damage that would be dealt to you and other permanents you control is dealt to this creature instead.
        this.addAbility(new SimpleStaticAbility(new AncientAdamantoiseRedirectEffect()));

        // When this creature dies, exile it and create ten tapped Treasure tokens.
        Ability ability = new DiesSourceTriggeredAbility(new ExileSourceEffect().setText("exile it"));
        ability.addEffect(new CreateTokenEffect(new TreasureToken(), 10, true).concatBy("and"));
        this.addAbility(ability);
    }

    private AncientAdamantoise(final AncientAdamantoise card) {
        super(card);
    }

    @Override
    public AncientAdamantoise copy() {
        return new AncientAdamantoise(this);
    }
}

class AncientAdamantoiseDamageEffect extends ContinuousRuleModifyingEffectImpl {

    AncientAdamantoiseDamageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "damage isn't removed from {this} during cleanup steps";
    }

    private AncientAdamantoiseDamageEffect(final AncientAdamantoiseDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REMOVE_DAMAGE_EOT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public AncientAdamantoiseDamageEffect copy() {
        return new AncientAdamantoiseDamageEffect(this);
    }
}

class AncientAdamantoiseRedirectEffect extends ReplacementEffectImpl {

    AncientAdamantoiseRedirectEffect() {
        super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
        staticText = "all damage that would be dealt to you and other " +
                "permanents you control is dealt to this creature instead";
    }

    private AncientAdamantoiseRedirectEffect(final AncientAdamantoiseRedirectEffect effect) {
        super(effect);
    }

    @Override
    public AncientAdamantoiseRedirectEffect copy() {
        return new AncientAdamantoiseRedirectEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent != null) {
            permanent.damage(
                    damageEvent.getAmount(), event.getSourceId(), source, game,
                    damageEvent.isCombatDamage(), damageEvent.isPreventable()
            );
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                return source.isControlledBy(event.getTargetId());
            case DAMAGE_PERMANENT:
                return !event.getTargetId().equals(source.getSourceId())
                        && source.isControlledBy(game.getControllerId(event.getTargetId()));
            default:
                return false;
        }
    }
}
