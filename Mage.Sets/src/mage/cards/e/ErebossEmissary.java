package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasSubtypeCondition;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class ErebossEmissary extends CardImpl {

    public ErebossEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bestow {5}{B}
        this.addAbility(new BestowAbility(this, "{5}{B}"));

        // Discard a creature card: Erebos's Emissary gets +2/+2 until end of turn. If Erebos's Emissary is an Aura, enchanted creature gets +2/+2 until end of turn instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2, 2, Duration.EndOfTurn),
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new SourceHasSubtypeCondition(SubType.AURA),
                "{this} gets +2/+2 until end of turn. If Erebos's Emissary is an Aura, enchanted creature gets +2/+2 until end of turn instead"),
                new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE))));

        // Enchanted creature gets +3/+3
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 3, Duration.WhileOnBattlefield)));
    }

    private ErebossEmissary(final ErebossEmissary card) {
        super(card);
    }

    @Override
    public ErebossEmissary copy() {
        return new ErebossEmissary(this);
    }
}
