package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoneyardLurker extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent card from your graveyard");

    public BoneyardLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Mutate {2}{B/G}{B/G}
        this.addAbility(new MutateAbility(this, "{2}{B/G}{B/G}"));

        // Whenever this creature mutates, return target permanent card from your graveyard to your hand.
        Ability ability = new MutatesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private BoneyardLurker(final BoneyardLurker card) {
        super(card);
    }

    @Override
    public BoneyardLurker copy() {
        return new BoneyardLurker(this);
    }
}
