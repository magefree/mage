package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandCard;
import mage.game.command.emblems.WrennAndSixEmblem;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrennAndSix extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("land card from your graveyard");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public WrennAndSix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WRENN);
        this.setStartingLoyalty(3);

        // +1: Return up to one target land card from your graveyard to your hand.
        Ability ability = new LoyaltyAbility(new ReturnFromGraveyardToHandTargetEffect(), 1);
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);

        // -1: Wrenn and Six deals 1 damage to any target.
        ability = new LoyaltyAbility(new DamageTargetEffect(1), -1);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // -7: You get an emblem with "Instant and sorcery cards in your graveyard have retrace."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new WrennAndSixEmblem()), -7));
    }

    private WrennAndSix(final WrennAndSix card) {
        super(card);
    }

    @Override
    public WrennAndSix copy() {
        return new WrennAndSix(this);
    }
}
