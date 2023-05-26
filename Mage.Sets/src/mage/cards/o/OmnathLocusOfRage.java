
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.OmnathElementalToken;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class OmnathLocusOfRage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elemental you control");

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public OmnathLocusOfRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, create a 5/5 red and green Elemental creature token.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new OmnathElementalToken()), false));

        // Whenever Omnath, Locus of Rage or another Elemental you control dies, Omnath deals 3 damage to any target.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(new DamageTargetEffect(3), false, filter);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private OmnathLocusOfRage(final OmnathLocusOfRage card) {
        super(card);
    }

    @Override
    public OmnathLocusOfRage copy() {
        return new OmnathLocusOfRage(this);
    }
}
