package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpeciesSpecialist extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    public SpeciesSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As Species Specialist enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.DrawCard)));

        // Whenever a creature of the chosen type dies, you may draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1), true, filter
        ));
    }

    private SpeciesSpecialist(final SpeciesSpecialist card) {
        super(card);
    }

    @Override
    public SpeciesSpecialist copy() {
        return new SpeciesSpecialist(this);
    }
}
