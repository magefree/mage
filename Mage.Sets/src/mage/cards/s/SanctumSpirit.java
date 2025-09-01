package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.HistoricPredicate;

import java.util.UUID;

/**
 *
 * @author keelahnkhan
 */
public final class SanctumSpirit extends CardImpl {

    private static final FilterCard filter = new FilterCard("a historic card");
    static {
        filter.add(HistoricPredicate.instance);
    }

    public SanctumSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Discard a historic card: Sanctum Spirit gains indestructible until end of turn.
        this.addAbility(
            new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), 
                new DiscardCardCost(filter)
            )
        );
    }

    private SanctumSpirit(final SanctumSpirit card) {
        super(card);
    }

    @Override
    public SanctumSpirit copy() {
        return new SanctumSpirit(this);
    }
}
