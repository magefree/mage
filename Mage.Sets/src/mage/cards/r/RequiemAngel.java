package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author intimidatingant
 */
public final class RequiemAngel extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another non-Spirit creature you control");

    static {
        filter.add(Predicates.not(SubType.SPIRIT.getPredicate()));
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RequiemAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever another non-Spirit creature you control dies, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken(), 1), false, filter));
    }

    private RequiemAngel(final RequiemAngel card) {
        super(card);
    }

    @Override
    public RequiemAngel copy() {
        return new RequiemAngel(this);
    }
}
