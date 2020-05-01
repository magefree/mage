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
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThornMammoth extends CardImpl {

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
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
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
