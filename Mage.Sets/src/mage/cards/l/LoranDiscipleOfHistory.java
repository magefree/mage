package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoranDiscipleOfHistory extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public LoranDiscipleOfHistory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Loran, Disciple of History or another legendary creature enters the battlefield under your control, return target artifact card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), filter, false, true
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private LoranDiscipleOfHistory(final LoranDiscipleOfHistory card) {
        super(card);
    }

    @Override
    public LoranDiscipleOfHistory copy() {
        return new LoranDiscipleOfHistory(this);
    }
}
