package mage.cards.e;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
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
        

        // At the beginning of your end step, each opponent loses X life and you gain X life, where X is the number of Auras you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new LoseLifeOpponentsEffect(ErietteOfTheCharmedAppleValue.instance), TargetController.YOU, false);
        ability.addEffect(new GainLifeEffect(ErietteOfTheCharmedAppleValue.instance));
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

    private FilterPermanent filter = new FilterPermanent("Auras you control");

    {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter.add(SubType.AURA.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().countAll(filter, sourceAbility.getControllerId(), game);
    }

    @Override
    public ErietteOfTheCharmedAppleValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the amount of life they lost this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}