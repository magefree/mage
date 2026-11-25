package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DifferentlyNamedPermanentCount;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;

import java.util.UUID;

/**
 * @author muz
 */
public final class CaptainPikeFullnessOfTime extends CardImpl {

    private static final DifferentlyNamedPermanentCount landCount =
        new DifferentlyNamedPermanentCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);

    public CaptainPikeFullnessOfTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Strange new worlds -- Captain Pike's power and toughness are each equal to the number of differently named lands you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new SetBasePowerToughnessSourceEffect(landCount)
                .setText("{this}'s power and toughness are each equal to the number of differently named lands you control"))
            .withFlavorWord("Strange new worlds")
            .addHint(landCount.getHint()
        ));

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play lands from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayFromTopOfLibraryEffect(new FilterLandCard("play lands"))));
    }

    private CaptainPikeFullnessOfTime(final CaptainPikeFullnessOfTime card) {
        super(card);
    }

    @Override
    public CaptainPikeFullnessOfTime copy() {
        return new CaptainPikeFullnessOfTime(this);
    }
}
