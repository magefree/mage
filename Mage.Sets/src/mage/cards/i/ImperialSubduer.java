package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImperialSubduer extends CardImpl {

    public ImperialSubduer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a Samurai or Warrior you control attacks alone, tap target creature you don't control.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
                new TapTargetEffect(),
                StaticFilters.FILTER_CONTROLLED_SAMURAI_OR_WARRIOR,
                false, false
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private ImperialSubduer(final ImperialSubduer card) {
        super(card);
    }

    @Override
    public ImperialSubduer copy() {
        return new ImperialSubduer(this);
    }
}
