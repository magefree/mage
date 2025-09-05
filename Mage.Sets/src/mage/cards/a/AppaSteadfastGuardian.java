package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.AllyToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AppaSteadfastGuardian extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("other target nonland permanents you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AppaSteadfastGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BISON);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Appa enters, airbend any number of other target nonland permanents you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AirbendTargetEffect());
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter));
        this.addAbility(ability);

        // Whenever you cast a spell from exile, create a 1/1 white Ally creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new AllyToken()), StaticFilters.FILTER_SPELL_A,
                false, SetTargetPointer.NONE, Zone.EXILED
        ));
    }

    private AppaSteadfastGuardian(final AppaSteadfastGuardian card) {
        super(card);
    }

    @Override
    public AppaSteadfastGuardian copy() {
        return new AppaSteadfastGuardian(this);
    }
}
