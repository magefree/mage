package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RescueSkiff extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or enchantment card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public RescueSkiff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}{W}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, return target creature or enchantment card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 10+
        // Flying
        // 5/6
        this.addAbility(new StationLevelAbility(10)
                .withLevelAbility(FlyingAbility.getInstance())
                .withPT(5, 6));
    }

    private RescueSkiff(final RescueSkiff card) {
        super(card);
    }

    @Override
    public RescueSkiff copy() {
        return new RescueSkiff(this);
    }
}
