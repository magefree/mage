package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Battlecruiser extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Mountains you control");

    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    public Battlecruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}{R}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Battlecruiser enters the battlefield, you may have it deal damage to target creature equal to the number of Mountains you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public Battlecruiser(final Battlecruiser card) {
        super(card);
    }

    @Override
    public Battlecruiser copy() {
        return new Battlecruiser(this);
    }
}
