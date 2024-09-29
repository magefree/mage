package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KellanTheFaeBlooded extends AdventureCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");
    private static final FilterCard auraOrEquipmentCard = new FilterCard("Aura or Equipment card");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        auraOrEquipmentCard.add(Predicates.or(
                SubType.EQUIPMENT.getPredicate(),
                SubType.AURA.getPredicate()
        ));
    }

    public KellanTheFaeBlooded(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{2}{R}", "Birthright Boon", "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Other creatures you control get +1/+0 for each Aura and Equipment attached to Kellan, the Fae-Blooded.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(
                        new AdditiveDynamicValue(new AuraAttachedCount(), new EquipmentAttachedCount()),
                        StaticValue.get(0), Duration.WhileOnBattlefield, filter, true
                ).setText("Other creatures you control get +1/+0 for each Aura and Equipment attached to {this}.")
        ));

        // Birthright Boon
        // Search your library for an Aura or Equipment card, reveal it, put it into your hand, then shuffle.
        this.getSpellCard().getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(auraOrEquipmentCard), true));

        this.finalizeAdventure();
    }

    private KellanTheFaeBlooded(final KellanTheFaeBlooded card) {
        super(card);
    }

    @Override
    public KellanTheFaeBlooded copy() {
        return new KellanTheFaeBlooded(this);
    }
}
