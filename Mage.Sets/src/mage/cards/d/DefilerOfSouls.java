package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MonocoloredPredicate;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class DefilerOfSouls extends CardImpl {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("monocolored creature");
    static {
        filter.add(MonocoloredPredicate.instance);
    }

    public DefilerOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{R}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // At the beginning of each player's upkeep, that player sacrifices a monocolored creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new SacrificeEffect(filter, 1, "that player"), TargetController.ANY, false));
    }

    private DefilerOfSouls(final DefilerOfSouls card) {
        super(card);
    }

    @Override
    public DefilerOfSouls copy() {
        return new DefilerOfSouls(this);
    }
}
