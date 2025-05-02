package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class DuneDrifter extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact or creature card with mana value X or less from your graveyard");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    public DuneDrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}{W}{B}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this Vehicle enters, return target artifact or creature card with mana value X or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.setTargetAdjuster(new XManaValueTargetAdjuster(ComparisonType.OR_LESS));
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private DuneDrifter(final DuneDrifter card) {
        super(card);
    }

    @Override
    public DuneDrifter copy() {
        return new DuneDrifter(this);
    }
}
