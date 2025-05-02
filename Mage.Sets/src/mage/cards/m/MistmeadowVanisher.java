package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MistmeadowVanisher extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland, nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public MistmeadowVanisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W/U}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Mistmeadow Vanisher becomes tapped, exile up to one target nonland, nontoken permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new ExileReturnBattlefieldNextEndStepTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private MistmeadowVanisher(final MistmeadowVanisher card) {
        super(card);
    }

    @Override
    public MistmeadowVanisher copy() {
        return new MistmeadowVanisher(this);
    }
}
