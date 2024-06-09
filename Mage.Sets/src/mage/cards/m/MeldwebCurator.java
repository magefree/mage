package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeldwebCurator extends CardImpl {

    public MeldwebCurator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Meldweb Curator enters the battlefield, put up to one target instant or sorcery card from your graveyard on top of your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true));
        ability.addTarget(new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
        ));
        this.addAbility(ability);
    }

    private MeldwebCurator(final MeldwebCurator card) {
        super(card);
    }

    @Override
    public MeldwebCurator copy() {
        return new MeldwebCurator(this);
    }
}
