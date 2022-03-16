package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImperialRecoveryUnit extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("creature or Vehicle card with mana value 2 or less from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ImperialRecoveryUnit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Imperial Recovery Unit attacks, return target creature or Vehicle card with mana value 2 or less from your graveyard to your hand.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private ImperialRecoveryUnit(final ImperialRecoveryUnit card) {
        super(card);
    }

    @Override
    public ImperialRecoveryUnit copy() {
        return new ImperialRecoveryUnit(this);
    }
}
