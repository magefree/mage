package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GladiolusAmicitia extends CardImpl {

    public GladiolusAmicitia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Gladiolus Amicitia enters, search your library for a land card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_LAND), true)
        ));

        // Landfall -- Whenever a land you control enters, another target creature you control gets +2/+2 and gains trample until end of turn.
        Ability ability = new LandfallAbility(new BoostTargetEffect(2, 2)
                .setText("another target creature you control gets +2/+2"));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private GladiolusAmicitia(final GladiolusAmicitia card) {
        super(card);
    }

    @Override
    public GladiolusAmicitia copy() {
        return new GladiolusAmicitia(this);
    }
}
