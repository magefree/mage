package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BloodToken;

/**
 *
 * @author weirddan455
 */
public final class BloodvialPurveyor extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Blood token");

    static {
        filter.add(SubType.BLOOD.getPredicate());
        filter.add(TokenPredicate.TRUE);
    }

    public BloodvialPurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever an opponent casts a spell, that player creates a Blood token.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenTargetEffect(new BloodToken()),
                StaticFilters.FILTER_SPELL_A, false,
                SetTargetPointer.PLAYER
        ));

        // Whenever Bloodvial Purveyor attacks, it gets +1/+0 until end of turn for each Blood token defending player controls.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(new PermanentsTargetOpponentControlsCount(filter), StaticValue.get(0), Duration.EndOfTurn, true)
                        .setText("it gets +1/+0 until end of turn for each Blood token defending player controls"),
                false, null, SetTargetPointer.PLAYER
        ));
    }

    private BloodvialPurveyor(final BloodvialPurveyor card) {
        super(card);
    }

    @Override
    public BloodvialPurveyor copy() {
        return new BloodvialPurveyor(this);
    }
}
