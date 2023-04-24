package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.ModifyCountersAddedEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PirImaginativeRascal extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent your team controls");

    static {
        filter.add(TargetController.TEAM.getControllerPredicate());
    }

    public PirImaginativeRascal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Partner with Toothy, Imaginary Friend (When this creature enters the battlefield, target player may put Toothy into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Toothy, Imaginary Friend", true));

        // If one or more counters would be put on a permanent your team controls, that many plus one of each of those kinds of counters are put on that permanent instead.
        this.addAbility(new SimpleStaticAbility(new ModifyCountersAddedEffect(filter, null)));
    }

    private PirImaginativeRascal(final PirImaginativeRascal card) {
        super(card);
    }

    @Override
    public PirImaginativeRascal copy() {
        return new PirImaginativeRascal(this);
    }
}
