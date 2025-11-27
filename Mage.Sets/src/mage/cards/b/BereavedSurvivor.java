package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BereavedSurvivor extends TransformingDoubleFacedCard {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }
    public BereavedSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{2}{W}",
                "Dauntless Avenger",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "W");

        // Bereaved Survivor
        this.getLeftHalfCard().setPT(2, 1);

        // When another creature you control dies, transform Bereaved Survivor.
        this.getLeftHalfCard().addAbility(new DiesCreatureTriggeredAbility(
                new TransformSourceEffect(), false,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ).setTriggerPhrase("When another creature you control dies, "));

        // Dauntless Avenger
        this.getRightHalfCard().setPT(3, 2);

        // Whenever Dauntless Avenger attacks, return target creature card with mana value 2 or less from your graveyard to the battlefield tapped and attacking.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(true, true));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.getRightHalfCard().addAbility(ability);
    }

    private BereavedSurvivor(final BereavedSurvivor card) {
        super(card);
    }

    @Override
    public BereavedSurvivor copy() {
        return new BereavedSurvivor(this);
    }
}
