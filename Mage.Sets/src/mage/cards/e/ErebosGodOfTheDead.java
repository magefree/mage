
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LevelX2
 */
public final class ErebosGodOfTheDead extends CardImpl {

    public ErebosGodOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to black is less than five, Erebos isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.B), 5);
        effect.setText("As long as your devotion to black is less than five, Erebos isn't a creature.<i>(Each {B} in the mana costs of permanents you control counts towards your devotion to black.)</i>");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Your opponents can't gain life.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new CantGainLifeAllEffect(
                        Duration.WhileOnBattlefield,
                        TargetController.OPPONENT
                )
        ));

        // {1}{B}, Pay 2 life: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);

    }

    public ErebosGodOfTheDead(final ErebosGodOfTheDead card) {
        super(card);
    }

    @Override
    public ErebosGodOfTheDead copy() {
        return new ErebosGodOfTheDead(this);
    }
}
