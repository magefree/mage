package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesWithLessPowerEffect;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class FeastingHobbit extends CardImpl {

    public FeastingHobbit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devour Food 3
        this.addAbility(new DevourAbility(3, StaticFilters.FILTER_CONTROLLED_FOOD));

        // Creatures with power less than Feasting Hobbit's power can't block it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByCreaturesWithLessPowerEffect()));
    }

    private FeastingHobbit(final FeastingHobbit card) {
        super(card);
    }

    @Override
    public FeastingHobbit copy() {
        return new FeastingHobbit(this);
    }
}
