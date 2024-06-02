package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DealsDamageToYouAllTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class Dread extends CardImpl {

    public Dread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Fear
        this.addAbility(FearAbility.getInstance());
        
        // Whenever a creature deals damage to you, destroy it.
        this.addAbility(new DealsDamageToYouAllTriggeredAbility(StaticFilters.FILTER_PERMANENT_CREATURE,
                new DestroyTargetEffect().setText("destroy it")));
        
        // When Dread is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    private Dread(final Dread card) {
        super(card);
    }

    @Override
    public Dread copy() {
        return new Dread(this);
    }
}
