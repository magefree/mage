package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */

public final class ThassaGodOfTheSea extends CardImpl {

    private static final DynamicValue xValue = new DevotionCount(ColoredManaSymbol.U);

    public ThassaGodOfTheSea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to white is less than five, Thassa isn't a creature.<i>(Each {U} in the mana costs of permanents you control counts towards your devotion to white.)</i>
        Effect effect = new LoseCreatureTypeSourceEffect(xValue, 5);
        effect.setText("As long as your devotion to blue is less than five, Thassa isn't a creature.<i>(Each {U} in the mana costs of permanents you control counts towards your devotion to blue.)</i>");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(new ValueHint("Devotion to blue", xValue)));

        // At the beginning of your upkeep, scry 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ScryEffect(1), TargetController.YOU, false));

        // {1}{U}: Target creature you control can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(Duration.EndOfTurn), new ManaCostsImpl("{1}{U}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public ThassaGodOfTheSea(final ThassaGodOfTheSea card) {
        super(card);
    }

    @Override
    public ThassaGodOfTheSea copy() {
        return new ThassaGodOfTheSea(this);
    }
}
