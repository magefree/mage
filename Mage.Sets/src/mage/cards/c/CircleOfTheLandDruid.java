package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
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
public final class CircleOfTheLandDruid extends CardImpl {

    public CircleOfTheLandDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.GNOME);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Circle of the Land Druid enters the battlefield, you may mill four cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(4), true));

        // Natural Recovery — When Circle of the Land Druid dies, return target land card from your graveyard to your hand.
        Ability ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_LAND));
        this.addAbility(ability.withFlavorWord("Natural Recovery"));
    }

    private CircleOfTheLandDruid(final CircleOfTheLandDruid card) {
        super(card);
    }

    @Override
    public CircleOfTheLandDruid copy() {
        return new CircleOfTheLandDruid(this);
    }
}
