
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class AvacynGuardianAngel extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public AvacynGuardianAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {1}{W}: Prevent all damage that would be dealt to another target creature this turn by sources of the color of your choice.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AvacynGuardianAngelPreventToCreatureEffect(),
                new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // {5}{W}{W}: Prevent all damage that would be dealt to target player this turn by sources of the color of your choice.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AvacynGuardianAngelPreventToPlayerEffect(),
                new ManaCostsImpl<>("{5}{W}{W}"));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    public AvacynGuardianAngel(final AvacynGuardianAngel card) {
        super(card);
    }

    @Override
    public AvacynGuardianAngel copy() {
        return new AvacynGuardianAngel(this);
    }
}

class AvacynGuardianAngelPreventToCreatureEffect extends OneShotEffect {

    AvacynGuardianAngelPreventToCreatureEffect() {
        super(Outcome.PreventDamage);
        this.staticText = "Prevent all damage that would be dealt to another target creature this turn by sources of the color of your choice";
    }

    AvacynGuardianAngelPreventToCreatureEffect(final AvacynGuardianAngelPreventToCreatureEffect effect) {
        super(effect);
    }

    @Override
    public AvacynGuardianAngelPreventToCreatureEffect copy() {
        return new AvacynGuardianAngelPreventToCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ChoiceColor choice = new ChoiceColor();
            if (controller.choose(Outcome.PreventDamage, choice, game)) {
                game.addEffect(new AvacynGuardianAngelPreventToCreaturePreventionEffect(choice.getColor()), source);
                return true;
            }
        }
        return false;
    }
}

class AvacynGuardianAngelPreventToCreaturePreventionEffect extends PreventionEffectImpl {

    private final ObjectColor color;

    AvacynGuardianAngelPreventToCreaturePreventionEffect(ObjectColor color) {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.color = color;
    }

    AvacynGuardianAngelPreventToCreaturePreventionEffect(AvacynGuardianAngelPreventToCreaturePreventionEffect effect) {
        super(effect);
        this.color = effect.color;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getType() == GameEvent.EventType.DAMAGE_CREATURE
                    && event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
                MageObject sourceObject = game.getObject(event.getSourceId());
                if (sourceObject != null && sourceObject.getColor(game).shares(this.color)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AvacynGuardianAngelPreventToCreaturePreventionEffect copy() {
        return new AvacynGuardianAngelPreventToCreaturePreventionEffect(this);
    }
}

class AvacynGuardianAngelPreventToPlayerEffect extends OneShotEffect {

    AvacynGuardianAngelPreventToPlayerEffect() {
        super(Outcome.PreventDamage);
        this.staticText = "Prevent all damage that would be dealt to target player or planeswalker this turn by sources of the color of your choice";
    }

    AvacynGuardianAngelPreventToPlayerEffect(final AvacynGuardianAngelPreventToPlayerEffect effect) {
        super(effect);
    }

    @Override
    public AvacynGuardianAngelPreventToPlayerEffect copy() {
        return new AvacynGuardianAngelPreventToPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ChoiceColor choice = new ChoiceColor();
            if (controller.choose(Outcome.PreventDamage, choice, game)) {
                game.addEffect(new AvacynGuardianAngelPreventToPlayerPreventionEffect(choice.getColor()), source);
                return true;
            }
        }
        return false;
    }
}

class AvacynGuardianAngelPreventToPlayerPreventionEffect extends PreventionEffectImpl {

    private final ObjectColor color;

    AvacynGuardianAngelPreventToPlayerPreventionEffect(ObjectColor color) {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.color = color;
    }

    AvacynGuardianAngelPreventToPlayerPreventionEffect(AvacynGuardianAngelPreventToPlayerPreventionEffect effect) {
        super(effect);
        this.color = effect.color;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if ((event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                    || event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER)
                    && event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
                MageObject sourceObject = game.getObject(event.getSourceId());
                if (sourceObject != null && sourceObject.getColor(game).shares(this.color)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AvacynGuardianAngelPreventToPlayerPreventionEffect copy() {
        return new AvacynGuardianAngelPreventToPlayerPreventionEffect(this);
    }
}
