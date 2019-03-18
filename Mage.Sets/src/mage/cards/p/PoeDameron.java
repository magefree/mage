package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author NinthWorld
 */
public final class PoeDameron extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("{this} or another nontoken creature you control");
    private static final FilterCreaturePermanent filterStarship = new FilterCreaturePermanent("starship creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(TokenPredicate.instance));
        filterStarship.add(new ControllerPredicate(TargetController.YOU));
        filterStarship.add(new SubtypePredicate(SubType.STARSHIP));
    }

    public PoeDameron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Poe Dameron or another nontoken creature enters the battlefield under your control, starship creatures you control get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.EndOfTurn, filterStarship, false), filter, false));
    }

    public PoeDameron(final PoeDameron card) {
        super(card);
    }

    @Override
    public PoeDameron copy() {
        return new PoeDameron(this);
    }
}
