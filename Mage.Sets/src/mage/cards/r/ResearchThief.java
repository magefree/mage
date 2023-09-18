package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResearchThief extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactCreaturePermanent("an artifact creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ResearchThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an artifact creature you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                filter, false, SetTargetPointer.NONE, true
        ));
    }

    private ResearchThief(final ResearchThief card) {
        super(card);
    }

    @Override
    public ResearchThief copy() {
        return new ResearchThief(this);
    }
}
