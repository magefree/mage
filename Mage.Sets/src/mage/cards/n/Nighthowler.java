package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Nighthowler extends CardImpl {

    private static final DynamicValue graveCreatures = new CardsInAllGraveyardsCount(StaticFilters.FILTER_CARD_CREATURE);

    public Nighthowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Bestow {2}{B}{B}
        this.addAbility(new BestowAbility(this, "{2}{B}{B}"));

        // Nighthowler and enchanted creature each get +X/+X, where X is the number of creature cards in all graveyards.
        Ability ability = new SimpleStaticAbility(new BoostSourceEffect(
                graveCreatures, graveCreatures, Duration.WhileOnBattlefield
        ).setText("{this} and enchanted creature each get +X/+X"));
        ability.addEffect(new BoostEnchantedEffect(
                graveCreatures, graveCreatures, Duration.WhileOnBattlefield
        ).setText(", where X is the number of creature cards in all graveyards"));
        this.addAbility(ability);
    }

    private Nighthowler(final Nighthowler card) {
        super(card);
    }

    @Override
    public Nighthowler copy() {
        return new Nighthowler(this);
    }
}
