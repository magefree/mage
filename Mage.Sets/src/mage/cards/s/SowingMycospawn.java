package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SowingMycospawn extends CardImpl {

    public SowingMycospawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Kicker {1}{C}
        this.addAbility(new KickerAbility("{1}{C}"));

        // When you cast this spell, search your library for a land card, put it onto the battlefield, then shuffle.
        this.addAbility(new CastSourceTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_LAND_A)
        )));

        // When you cast this spell, if it was kicked, exile target land.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new CastSourceTriggeredAbility(new ExileTargetEffect()),
                KickedCondition.ONCE, "When you cast this spell, " +
                "if it was kicked, exile target land."
        );
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private SowingMycospawn(final SowingMycospawn card) {
        super(card);
    }

    @Override
    public SowingMycospawn copy() {
        return new SowingMycospawn(this);
    }
}
