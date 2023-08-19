package mage.cards.e;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author Xanderhall
 */
public final class ErietteOfTheCharmedApple extends CardImpl {

    public ErietteOfTheCharmedApple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Each creature that's enchanted by an Aura you control can't attack you or planeswalkers you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ErietteOfTheCharmedAppleEffect()));

        // At the beginning of your end step, each opponent loses X life and you gain X life, where X is the number of Auras you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new LoseLifeOpponentsEffect(ErietteOfTheCharmedAppleValue.instance), TargetController.YOU, false);
        ability.addEffect(new GainLifeEffect(ErietteOfTheCharmedAppleValue.instance));
        this.addAbility(ability);
    }

    private ErietteOfTheCharmedApple(final ErietteOfTheCharmedApple card) {
        super(card);
    }

    @Override
    public ErietteOfTheCharmedApple copy() {
        return new ErietteOfTheCharmedApple(this);
    }
}

class ErietteOfTheCharmedAppleEffect extends ContinuousEffectImpl {

    ErietteOfTheCharmedAppleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "Each creature that's enchanted by an Aura you control can't attack you or planeswalkers you control.";
    }

    private ErietteOfTheCharmedAppleEffect(final ErietteOfTheCharmedAppleEffect effect) {
        super(effect);
    }

    @Override
    public ErietteOfTheCharmedAppleEffect copy() {
        return new ErietteOfTheCharmedAppleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Implementation taken from Kaima, The Fractured Calm
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
            if (permanent.getAttachments()
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .noneMatch(p -> p.isControlledBy(source.getControllerId()) && p.hasSubtype(SubType.AURA, game))
            ) {
                continue;
            }
            game.addEffect(new CantAttackYouAllEffect(Duration.Custom).setTargetPointer(new FixedTarget(permanent, game)), source);
        }

        return true;
    }

}

enum ErietteOfTheCharmedAppleValue implements DynamicValue {
    instance;

    private static FilterPermanent filter = new FilterPermanent("Auras you control");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter.add(SubType.AURA.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        FilterPermanent controllerFilter = filter.copy();
        controllerFilter.add(new ControllerIdPredicate(sourceAbility.getControllerId()));
        return game.getBattlefield().count(controllerFilter, sourceAbility.getControllerId(), sourceAbility, game);
    }

    @Override
    public ErietteOfTheCharmedAppleValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the amount of Auras you control";
    }
}