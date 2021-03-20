package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShimmerwingChimera extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledEnchantmentPermanent("other target enchantment you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ShimmerwingChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, return up to one other target enchantment you control to its owner's hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new ReturnToHandTargetEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private ShimmerwingChimera(final ShimmerwingChimera card) {
        super(card);
    }

    @Override
    public ShimmerwingChimera copy() {
        return new ShimmerwingChimera(this);
    }
}
