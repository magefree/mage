package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class GnawingVermin extends CardImpl {

    public GnawingVermin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Gnawing Vermin enters the battlefield, target player mills two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When Gnawing Vermin dies, target creature you don't control gets -1/-1 until end of turn.
        ability = new DiesSourceTriggeredAbility(new BoostTargetEffect(-1, -1));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private GnawingVermin(final GnawingVermin card) {
        super(card);
    }

    @Override
    public GnawingVermin copy() {
        return new GnawingVermin(this);
    }
}
