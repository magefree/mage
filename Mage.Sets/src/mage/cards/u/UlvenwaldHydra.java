
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class UlvenwaldHydra extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("lands you control");

    public UlvenwaldHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        DynamicValue controlledLands = new PermanentsOnBattlefieldCount(filter);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Ulvenwald Hydra's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(controlledLands, Duration.EndOfGame)));

        // When Ulvenwald Hydra enters the battlefield, you may search your library for a land card, put it onto the battlefield tapped, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(new FilterLandCard());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(target, true, true, Outcome.PutLandInPlay), true));
    }

    private UlvenwaldHydra(final UlvenwaldHydra card) {
        super(card);
    }

    @Override
    public UlvenwaldHydra copy() {
        return new UlvenwaldHydra(this);
    }
}
