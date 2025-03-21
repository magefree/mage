package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GarruksPackleader extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another creature you control with power 3 or greater");
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public GarruksPackleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), filter, true));
    }

    private GarruksPackleader(final GarruksPackleader card) {
        super(card);
    }

    @Override
    public GarruksPackleader copy() {
        return new GarruksPackleader(this);
    }

}
