package mage.cards.v;

import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.command.emblems.VenserTheSojournerEmblem;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class VenserTheSojourner extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public VenserTheSojourner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VENSER);

        this.setStartingLoyalty(3);

        // +2: Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step.
        LoyaltyAbility ability1 = new LoyaltyAbility(new ExileReturnBattlefieldNextEndStepTargetEffect().underYourControl(true).withTextThatCard(false), 2);
        Target target = new TargetPermanent(filter);
        ability1.addTarget(target);
        this.addAbility(ability1);

        // -1: Creatures can't be blocked this turn.
        this.addAbility(new LoyaltyAbility(new CantBeBlockedAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn), -1));

        // -8: You get an emblem with "Whenever you cast a spell, exile target permanent."
        LoyaltyAbility ability2 = new LoyaltyAbility(new GetEmblemEffect(new VenserTheSojournerEmblem()), -8);
        this.addAbility(ability2);
    }

    private VenserTheSojourner(final VenserTheSojourner card) {
        super(card);
    }

    @Override
    public VenserTheSojourner copy() {
        return new VenserTheSojourner(this);
    }

}
