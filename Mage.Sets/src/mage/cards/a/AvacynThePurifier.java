package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AvacynThePurifier extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AvacynThePurifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature transforms into Avacyn, the Purifier, it deals 3 damage to each other creature and each opponent.
        Ability ability = new TransformIntoSourceTriggeredAbility(
                new DamageAllEffect(3, "it", filter)
        );
        ability.addEffect(new DamagePlayersEffect(3, TargetController.OPPONENT).setText("and each opponent"));
        this.addAbility(ability);
    }

    private AvacynThePurifier(final AvacynThePurifier card) {
        super(card);
    }

    @Override
    public AvacynThePurifier copy() {
        return new AvacynThePurifier(this);
    }
}
