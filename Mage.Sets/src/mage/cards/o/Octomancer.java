package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Octomancer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature token that entered the battlefield this turn");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public Octomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Gift an Octopus
        this.addAbility(new GiftAbility(this, GiftType.OCTOPUS));

        // At the beginning of each end step, create a token that's a copy of target creature token that entered the battlefield this turn.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new CreateTokenCopyTargetEffect(), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private Octomancer(final Octomancer card) {
        super(card);
    }

    @Override
    public Octomancer copy() {
        return new Octomancer(this);
    }
}
