package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class RockcasterPlatoon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public RockcasterPlatoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // {4}{G}: Rockcaster Platoon deals 2 damage to each creature with flying and each player.
        Ability ability = new SimpleActivatedAbility(new DamageAllEffect(2, filter), new ManaCostsImpl<>("{4}{G}"));
        ability.addEffect(new DamagePlayersEffect(2).setText("and each player"));
        this.addAbility(ability);
    }

    private RockcasterPlatoon(final RockcasterPlatoon card) {
        super(card);
    }

    @Override
    public RockcasterPlatoon copy() {
        return new RockcasterPlatoon(this);
    }
}
