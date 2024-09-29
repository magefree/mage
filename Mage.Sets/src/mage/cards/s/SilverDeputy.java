package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SilverDeputy extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("a basic land card or a Desert card");

    static {
        filter.add(Predicates.or(SuperType.BASIC.getPredicate(), SubType.DESERT.getPredicate()));
    }

    public SilverDeputy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Silver Deputy enters the battlefield, you may search your library for a basic land card or a Desert card, reveal it, then shuffle and put it on top.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true),
                true
        ));

        // {T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new BoostTargetEffect(1, 0), new TapSourceCost()
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SilverDeputy(final SilverDeputy card) {
        super(card);
    }

    @Override
    public SilverDeputy copy() {
        return new SilverDeputy(this);
    }
}
