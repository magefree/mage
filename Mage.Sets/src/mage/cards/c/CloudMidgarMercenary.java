package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterPermanentThisOrAnother;
import mage.filter.predicate.permanent.AttachedToSourcePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloudMidgarMercenary extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Equipment card");
    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    private static final FilterPermanent subfilter = new FilterPermanent(SubType.EQUIPMENT, "an equipment attached to it");
    static {
        subfilter.add(AttachedToSourcePredicate.instance);
    }

    private static final FilterPermanentThisOrAnother filter2 = new FilterPermanentThisOrAnother(subfilter, false, "{this} or an equipment attached to it");

    public CloudMidgarMercenary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Cloud enters, search your library for an Equipment card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // As long as Cloud is equipped, if an ability of Cloud or an Equipment attached to it triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(filter2, EquippedSourceCondition.instance)).addHint(EquippedSourceCondition.getHint()));
    }

    private CloudMidgarMercenary(final CloudMidgarMercenary card) {
        super(card);
    }

    @Override
    public CloudMidgarMercenary copy() {
        return new CloudMidgarMercenary(this);
    }
}
