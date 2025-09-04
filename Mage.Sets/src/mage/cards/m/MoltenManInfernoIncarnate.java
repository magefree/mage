package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterBasicCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class MoltenManInfernoIncarnate extends CardImpl {

    public MoltenManInfernoIncarnate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // When this enters the battlefield, search your library for a basic Mountain card and put it onto the battlefield tapped. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterBasicCard(SubType.MOUNTAIN)), true)
        ));

        // This creature gets +1/+1 for each Mountain you control.
        DynamicValue value = new PermanentsOnBattlefieldCount(new FilterLandPermanent(SubType.MOUNTAIN, "Mountain you control"));
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(value, value, Duration.WhileOnBattlefield)));

        // When this leaves the battlefield, sacrifice a land.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(
                new SacrificeEffect(new FilterControlledLandPermanent("a land"), 1, ""))
        );
    }

    private MoltenManInfernoIncarnate(final MoltenManInfernoIncarnate card) {
        super(card);
    }

    @Override
    public MoltenManInfernoIncarnate copy() {
        return new MoltenManInfernoIncarnate(this);
    }
}
