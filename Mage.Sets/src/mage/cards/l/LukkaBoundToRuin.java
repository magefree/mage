package mage.cards.l;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.CompleatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.PhyrexianBeastToxicToken;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author miesma
 */
public class LukkaBoundToRuin extends CardImpl {

    public LukkaBoundToRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R/G/P}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LUKKA);
        this.setStartingLoyalty(5);

        // Compleated
        this.addAbility(CompleatedAbility.getInstance());

        // +1: Add {R}{G}. Spend this mana only to cast creature spells or activate abilities of creatures.
        Ability ability = new LoyaltyAbility(new LukkaBoundToRuinManaEffect(), 1);
        this.addAbility(ability);

        // −1: Create a 3/3 green Phyrexian Beast creature token with toxic 1.
        ability = new LoyaltyAbility(new CreateTokenEffect(new PhyrexianBeastToxicToken()), -1);
        this.addAbility(ability);

        // −4: Lukka deals X damage divided as you choose among any number of target creatures and/or planeswalkers,
        // where X is the greatest power among creatures you controlled as you activated this ability.
        DynamicValue xValue = GreatestPowerAmongControlledCreaturesValue.instance;
        DamageMultiEffect damageMultiEffect = new DamageMultiEffect(xValue);
        damageMultiEffect.setText("Lukka deals X damage divided as you choose " +
                "among any number of target creatures and/or planeswalkers, " +
                "where X is the greatest power among creatures you controlled as you activated this ability.");
        ability = new LoyaltyAbility(damageMultiEffect, -4);
        ability.setTargetAdjuster(LukkaBoundToRuinAdjuster.instance);
        this.addAbility(ability);
    }

    private LukkaBoundToRuin(final LukkaBoundToRuin card) {
        super(card);
    }

    @Override
    public LukkaBoundToRuin copy() {
        return new LukkaBoundToRuin(this);
    }
}

class LukkaBoundToRuinManaEffect extends OneShotEffect {

    LukkaBoundToRuinManaEffect() {
        super(Outcome.Benefit);
        staticText = "Add {R}{G}. Spend this mana only to cast creature spells or activate abilities of creatures.";
    }

    private LukkaBoundToRuinManaEffect(final LukkaBoundToRuinManaEffect effect) {
        super(effect);
    }

    @Override
    public LukkaBoundToRuinManaEffect copy() {
        return new LukkaBoundToRuinManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ConditionalMana mana = new LukkaBoundToRuinConditionalMana();
        player.getManaPool().addMana(mana, game, source);
        return true;
    }
}

class LukkaBoundToRuinConditionalMana extends ConditionalMana {

    // Add {R}{G}
    private static Mana mana = new Mana(0, 0, 0, 1, 1, 0, 0, 0);

    public LukkaBoundToRuinConditionalMana() {
        super(mana);
        addCondition(LukkaBoundToRuinManaCondition.instance);
    }
}

enum LukkaBoundToRuinManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        // Spend this mana only to cast creature spells or activate abilities of creatures.
        return object != null && object.isCreature(game);
    }
}

/**
 * Gatherer Rulings:
 * 04.02.2023
 * You can't choose more targets than the greatest power among creatures you control as you activate the ability,
 * and each chosen target must receive at least 1 damage.
 */
enum LukkaBoundToRuinAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        // Maximum targets is equal to the damage - as each target need to be assigned at least 1 damage
        ability.getTargets().clear();
        int xValue = GreatestPowerAmongControlledCreaturesValue.instance.calculate(game, ability, null);
        TargetCreatureOrPlaneswalkerAmount targetCreatureOrPlaneswalkerAmount = new TargetCreatureOrPlaneswalkerAmount(xValue);
        targetCreatureOrPlaneswalkerAmount.setMinNumberOfTargets(0);
        targetCreatureOrPlaneswalkerAmount.setMaxNumberOfTargets(xValue);
        ability.addTarget(targetCreatureOrPlaneswalkerAmount);
    }
}
