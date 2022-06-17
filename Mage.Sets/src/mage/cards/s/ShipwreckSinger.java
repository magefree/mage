package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ShipwreckSinger extends CardImpl {

    public ShipwreckSinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.SIREN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{U}: Target creature an opponent controls attacks this turn if able.
        Ability ability = new SimpleActivatedAbility(new AttacksIfAbleTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
        // {1}{B}, {T}: Attacking creatures get -1/-1 until end of turn.
        ability = new SimpleActivatedAbility(new BoostAllEffect(-1,-1, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ShipwreckSinger(final ShipwreckSinger card) {
        super(card);
    }

    @Override
    public ShipwreckSinger copy() {
        return new ShipwreckSinger(this);
    }
}
