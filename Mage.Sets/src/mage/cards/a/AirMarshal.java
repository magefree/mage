package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class AirMarshal extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SOLDIER, "Soldier");

    public AirMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {3}: Target Soldier gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance()), new GenericManaCost(3));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AirMarshal(final AirMarshal card) {
        super(card);
    }

    @Override
    public AirMarshal copy() {
        return new AirMarshal(this);
    }
}
