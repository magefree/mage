package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThornMammoth extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public ThornMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Thorn Mammoth or another creature enters the battlefield under your control, Thorn Mammoth fights up to one target creature you don't control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new FightTargetSourceEffect(), StaticFilters.FILTER_PERMANENT_CREATURE,
                "Whenever {this} or another creature enters the battlefield under your control, " +
                        "{this} fights up to one target creature you don't control."
        );
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private ThornMammoth(final ThornMammoth card) {
        super(card);
    }

    @Override
    public ThornMammoth copy() {
        return new ThornMammoth(this);
    }
}
