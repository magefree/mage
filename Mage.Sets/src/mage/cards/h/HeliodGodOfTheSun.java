
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HeliodGodOfTheSunToken;

/**
 *
 * @author LevelX2
 */
public final class HeliodGodOfTheSun extends CardImpl {

    public HeliodGodOfTheSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to white is less than five, Heliod isn't a creature.<i>(Each {W} in the mana costs of permanents you control counts towards your devotion to white.)</i>
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.W), 5);
        effect.setText("As long as your devotion to white is less than five, Heliod isn't a creature.<i>(Each {W} in the mana costs of permanents you control counts towards your devotion to white.)</i>");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Other creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, true)));

        // {2}{W}{W}: Create a 2/1 white Cleric enchantment creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new HeliodGodOfTheSunToken()), new ManaCostsImpl("{2}{W}{W}")));

    }

    public HeliodGodOfTheSun(final HeliodGodOfTheSun card) {
        super(card);
    }

    @Override
    public HeliodGodOfTheSun copy() {
        return new HeliodGodOfTheSun(this);
    }
}
