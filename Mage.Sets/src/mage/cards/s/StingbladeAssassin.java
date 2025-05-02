package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StingbladeAssassin extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent(
            "creature an opponent controls that was dealt damage this turn"
    );

    static {
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public StingbladeAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Stingblade Assassin enters the battlefield, destroy target creature an opponent controls that was dealt damage this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private StingbladeAssassin(final StingbladeAssassin card) {
        super(card);
    }

    @Override
    public StingbladeAssassin copy() {
        return new StingbladeAssassin(this);
    }
}
