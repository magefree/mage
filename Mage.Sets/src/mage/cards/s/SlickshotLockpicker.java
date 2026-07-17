package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainFlashbackTargetEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SlickshotLockpicker extends CardImpl {

    public SlickshotLockpicker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Slickshot Lockpicker enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainFlashbackTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        this.addAbility(ability);

        // Plot {2}{U}
        this.addAbility(new PlotAbility("{2}{U}"));
    }

    private SlickshotLockpicker(final SlickshotLockpicker card) {
        super(card);
    }

    @Override
    public SlickshotLockpicker copy() {
        return new SlickshotLockpicker(this);
    }
}
