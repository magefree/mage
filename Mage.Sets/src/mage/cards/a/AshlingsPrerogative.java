
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Eirkei
 */
public final class AshlingsPrerogative extends CardImpl {

    public AshlingsPrerogative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");

        // As Ashling's Prerogative enters the battlefield, choose odd or even.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Odd or even?", "Odd", "Even"), null, "As {this} enters the battlefield, choose odd or even. <i>(Zero is even.)</i>", ""));

        // Each creature with converted mana cost of the chosen value has haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AshlingsPrerogativeCorrectOddityEffect()));

        // Each creature without converted mana cost of the chosen value enters the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AshlingsPrerogativeIncorrectOddityEffect()));

    }

    private AshlingsPrerogative(final AshlingsPrerogative card) {
        super(card);
    }

    @Override
    public AshlingsPrerogative copy() {
        return new AshlingsPrerogative(this);
    }
}

class AshlingsPrerogativeIncorrectOddityEffect extends PermanentsEnterBattlefieldTappedEffect {

    private static final FilterCreaturePermanent creaturefilter = new FilterCreaturePermanent("Each creature without mana value of the chosen quality");
    private static final ModeChoiceSourceCondition oddCondition = new ModeChoiceSourceCondition("Odd");

    public AshlingsPrerogativeIncorrectOddityEffect() {
        super(creaturefilter);
        staticText = "Each creature without mana value of the chosen quality enters the battlefield tapped.";
    }
    
    private AshlingsPrerogativeIncorrectOddityEffect(final AshlingsPrerogativeIncorrectOddityEffect effect) {
        super(effect);   
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        int incorrectModResult;

        if (oddCondition.apply(game, source)) {
            incorrectModResult = 0;
        } else {
            incorrectModResult = 1;
        }

        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();

        return permanent != null && creaturefilter.match(permanent, game) && permanent.getManaValue() % 2 == incorrectModResult;
    }
    
    @Override
    public AshlingsPrerogativeIncorrectOddityEffect copy() {
        return new AshlingsPrerogativeIncorrectOddityEffect(this);
    }
}

class AshlingsPrerogativeCorrectOddityEffect extends GainAbilityAllEffect {

    private static final FilterCreaturePermanent creaturefilter = new FilterCreaturePermanent("Each creature with mana value of the chosen quality");
    private static final ModeChoiceSourceCondition oddCondition = new ModeChoiceSourceCondition("Odd");

    public AshlingsPrerogativeCorrectOddityEffect() {
        super(HasteAbility.getInstance(), Duration.WhileOnBattlefield, creaturefilter);
        staticText = "Each creature with mana value of the chosen quality has haste.";
    }
    private AshlingsPrerogativeCorrectOddityEffect(final AshlingsPrerogativeCorrectOddityEffect effect) {
        super(effect);   
    }

    @Override
    protected boolean selectedByRuntimeData(Permanent permanent, Ability source, Game game) {
        int correctModResult;
        if (oddCondition.apply(game, source)) {
            correctModResult = 1;
        } else {
            correctModResult = 0;
        }
        return permanent != null && creaturefilter.match(permanent, game) && permanent.getManaValue() % 2 == correctModResult;
    }
    
    @Override
    public AshlingsPrerogativeCorrectOddityEffect copy() {
        return new AshlingsPrerogativeCorrectOddityEffect(this);
    }
}
