package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class VictorTimelyWilyTycoon extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact, instant, or sorcery card with mana value 4 or less from your graveyard");

    static {
        filter.add(
            Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
            )
        );
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
    }

    public VictorTimelyWilyTycoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Victor Timely enters, you may cast target artifact, instant, or sorcery card with mana value 4 or less from your graveyard without paying its mana cost. If that spell would be put into your graveyard, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private VictorTimelyWilyTycoon(final VictorTimelyWilyTycoon card) {
        super(card);
    }

    @Override
    public VictorTimelyWilyTycoon copy() {
        return new VictorTimelyWilyTycoon(this);
    }
}
