package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarmonicProdigy extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Shaman or another Wizard you control");

    static {
        filter.add(Predicates.or(SubType.SHAMAN.getPredicate(), Predicates.and(new FilterPermanent(SubType.WIZARD).add(AnotherPredicate.instance).getPredicates())));
    }

    public HarmonicProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());

        // If an ability of a Shaman or another Wizard you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(filter)));
    }

    private HarmonicProdigy(final HarmonicProdigy card) {
        super(card);
    }

    @Override
    public HarmonicProdigy copy() {
        return new HarmonicProdigy(this);
    }
}
