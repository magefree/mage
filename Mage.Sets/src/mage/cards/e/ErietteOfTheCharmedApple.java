package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.EnchantedByPlayerPredicate;
import mage.game.Game;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Xanderhall
 */
public final class ErietteOfTheCharmedApple extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures enchanted by an Aura you control");
    private static final Hint hint = new ValueHint("Number of Auras you control", ErietteOfTheCharmedAppleValue.instance);

    public ErietteOfTheCharmedApple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Each creature that's enchanted by an Aura you control can't attack you or planeswalkers you control.
        filter.add(new EnchantedByPlayerPredicate(ownerId));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackYouAllEffect(Duration.EndOfGame, filter, true)));

        // At the beginning of your end step, each opponent loses X life and you gain X life, where X is the number of Auras you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new LoseLifeOpponentsEffect(ErietteOfTheCharmedAppleValue.instance), TargetController.YOU, false);
        ability.addEffect(new GainLifeEffect(ErietteOfTheCharmedAppleValue.instance));
        ability.addHint(hint);
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