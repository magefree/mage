
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Backfir3
 */
public final class KamahlFistOfKrosa extends CardImpl {

    public KamahlFistOfKrosa(UUID ownerId, CardSetInfo setInfo) {
        
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {G}: Target land becomes a 1/1 creature until end of turn. It's still a land.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesCreatureTargetEffect(new CreatureToken(1, 1), false, true, Duration.EndOfTurn),
                new ManaCostsImpl<>("{G}"));
		ability.addTarget(new TargetLandPermanent());
		this.addAbility(ability);
		
        // {2}{G}{G}{G}: Creatures you control get +3/+3 and gain trample until end of turn.
        SimpleActivatedAbility boostAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(3, 3, Duration.EndOfTurn),
                new ManaCostsImpl<>("{2}{G}{G}{G}"));
		boostAbility.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));
		this.addAbility(boostAbility);
    }

    private KamahlFistOfKrosa(final KamahlFistOfKrosa card) {
        super(card);
    }

    @Override
    public KamahlFistOfKrosa copy() {
        return new KamahlFistOfKrosa(this);
    }
}
