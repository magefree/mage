
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author LevelX2
 */
public final class Nighthowler extends CardImpl {

    public Nighthowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Bestow {2}{B}{B}
        this.addAbility(new BestowAbility(this, "{2}{B}{B}"));
        // Nighthowler and enchanted creature each get +X/+X, where X is the number of creature cards in all graveyards.
        DynamicValue graveCreatures = new CardsInAllGraveyardsCount(new FilterCreatureCard());
        Effect effect = new BoostSourceEffect(graveCreatures, graveCreatures, Duration.WhileOnBattlefield);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new BoostEnchantedEffect(graveCreatures, graveCreatures, Duration.WhileOnBattlefield);
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    public Nighthowler(final Nighthowler card) {
        super(card);
    }

    @Override
    public Nighthowler copy() {
        return new Nighthowler(this);
    }
}
