package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.HalflingToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaradocMasterOfBuckland extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("nontoken creature with power 2 or less");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.HALFLING, "other untapped Halflings you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(TappedPredicate.UNTAPPED);
        filter2.add(AnotherPredicate.instance);
    }

    public SaradocMasterOfBuckland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Saradoc, Master of Buckland or another nontoken creature with power 2 or less enters the battlefield under your control, create a 1/1 white Halfling creature token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new HalflingToken()), filter, false, true
        ));

        // Tap two other untapped Halflings you control: Saradoc gets +2/+0 and gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn)
                        .setText("{this} gets +2/+0"),
                new TapTargetCost(new TargetControlledPermanent(2, filter2))
        );
        ability.addEffect(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains lifelink until end of turn"));
        this.addAbility(ability);
    }

    private SaradocMasterOfBuckland(final SaradocMasterOfBuckland card) {
        super(card);
    }

    @Override
    public SaradocMasterOfBuckland copy() {
        return new SaradocMasterOfBuckland(this);
    }
}
