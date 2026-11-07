package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.OfficerToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class AcademyInstructor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.OFFICER, "Officer token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public AcademyInstructor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.BOLIAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // This creature gets +1/+0 for each Officer token you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
            new PermanentsOnBattlefieldCount(filter), StaticValue.get(0), Duration.WhileOnBattlefield
        )));

        // Whenever you cast a noncreature spell, create a 1/1 red Officer creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new CreateTokenEffect(new OfficerToken()), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private AcademyInstructor(final AcademyInstructor card) {
        super(card);
    }

    @Override
    public AcademyInstructor copy() {
        return new AcademyInstructor(this);
    }
}
