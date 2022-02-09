
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.game.command.emblems.NissaVitalForceEmblem;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class NissaVitalForce extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("land you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public NissaVitalForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);

        this.setStartingLoyalty(5);

        // +1: Untap target land you control. Until your next turn, it becomes a 5/5 Elemental creature with haste. It's still a land.
        LoyaltyAbility ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addEffect(new BecomesCreatureTargetEffect(new NissaVitalForceToken(), false, true, Duration.UntilYourNextTurn));
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);

        // -3: Return target permanent card from your graveyard to your hand.
        ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -3);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterPermanentCard("permanent card from your graveyard")));
        this.addAbility(ability);

        // -6: You get an emblem with "Whenever a land enters the battlefield under your control, you may draw a card."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new NissaVitalForceEmblem()), -6));
    }

    private NissaVitalForce(final NissaVitalForce card) {
        super(card);
    }

    @Override
    public NissaVitalForce copy() {
        return new NissaVitalForce(this);
    }
}

class NissaVitalForceToken extends TokenImpl {

    public NissaVitalForceToken() {
        super("", "5/5 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(HasteAbility.getInstance());
    }
    public NissaVitalForceToken(final NissaVitalForceToken token) {
        super(token);
    }

    public NissaVitalForceToken copy() {
        return new NissaVitalForceToken(this);
    }
}
