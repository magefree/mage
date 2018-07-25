package mage.cards.c;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class CreepColony extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zerg creature");

    static {
        filter.add(new SubtypePredicate(SubType.ZERG));
    }

    public CreepColony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Creep Colony enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Creep Colony enters the battlefield, target Zerg creature gets +1/+0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(1, 0, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // {T}: Add {B} or {G} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new TapSourceCost()));
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(1), new TapSourceCost()));
    }

    public CreepColony(final CreepColony card) {
        super(card);
    }

    @Override
    public CreepColony copy() {
        return new CreepColony(this);
    }
}
