package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class ErstwhileTrooper extends CardImpl {

    public ErstwhileTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Discard a creature card: Erstwhile Trooper gets +2/+2 and gains trample until end of turn. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(
                        2, 2, Duration.EndOfTurn
                ).setText("{this} gets +2/+2"),
                new DiscardTargetCost(new TargetCardInHand(
                        StaticFilters.FILTER_CARD_CREATURE_A
                ))
        );
        ability.addEffect(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        this.addAbility(ability);
    }

    private ErstwhileTrooper(final ErstwhileTrooper card) {
        super(card);
    }

    @Override
    public ErstwhileTrooper copy() {
        return new ErstwhileTrooper(this);
    }
}
