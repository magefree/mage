package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageWithExcessEffect;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class RavenousTyrannosaurus extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }
    public RavenousTyrannosaurus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Devour 3
        this.addAbility(new DevourAbility(3));

        // Whenever Ravenous Tyrannosaurus attacks, it deals damage equal to its power to up to one other target creature. Excess damage is dealt to that creature's controller instead.
        Ability ability = new AttacksTriggeredAbility(new DamageWithExcessEffect(new SourcePermanentPowerCount(false))
                .setText("it deals damage equal to its power to up to one other target creature. Excess damage is dealt to that creature's controller instead."));
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private RavenousTyrannosaurus(final RavenousTyrannosaurus card) {
        super(card);
    }

    @Override
    public RavenousTyrannosaurus copy() {
        return new RavenousTyrannosaurus(this);
    }
}
