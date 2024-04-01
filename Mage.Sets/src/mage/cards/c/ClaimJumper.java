package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ClaimJumper extends CardImpl {

    private static final FilterCard filter = new FilterCard("Plains card");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public ClaimJumper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Claim Jumper enters the battlefield, if an opponent controls more lands than you, you may search your library for a Plains card and put it onto the battlefield tapped. Then if an opponent controls more lands than you, repeat this process once. If you search your library this way, shuffle.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new SearchLibraryPutInPlayEffect(
                                new TargetCardInLibrary(0, 1, filter), true
                        ),
                        true
                ),
                new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS),
                "When Claim Jumper enters the battlefield, if an opponent controls more lands than you, "
                        + "you may search your library for a Plains card and put it onto the battlefield tapped. "
                        + "Then if an opponent controls more lands than you, repeat this process once. "
                        + "If you search your library this way, shuffle."
        );
        ability.addEffect(
                new ConditionalOneShotEffect(
                        new SearchLibraryPutInPlayEffect(
                                new TargetCardInLibrary(0, 1, filter), true, false, true
                        ),
                        new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS)
                )
        );
        this.addAbility(ability);
    }

    private ClaimJumper(final ClaimJumper card) {
        super(card);
    }

    @Override
    public ClaimJumper copy() {
        return new ClaimJumper(this);
    }
}
