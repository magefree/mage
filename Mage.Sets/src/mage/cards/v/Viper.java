package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttacksIfAbleTargetPlayerSourceEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Viper extends CardImpl {

    public Viper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {B}, {T}: Target creature an opponent controls attacks you this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AttacksIfAbleTargetEffect(Duration.EndOfTurn), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);

        // {G}, {T}: Target creature blocks this turn if able.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BlocksIfAbleTargetEffect(Duration.EndOfTurn), new ManaCostsImpl("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public Viper(final Viper card) {
        super(card);
    }

    @Override
    public Viper copy() {
        return new Viper(this);
    }
}
