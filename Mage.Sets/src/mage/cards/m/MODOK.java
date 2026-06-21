package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class MODOK extends CardImpl {

    public MODOK(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Mental Organism -- Pay 3 life: M.O.D.O.K. connives. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
            new ConniveSourceEffect("{this}"), new PayLifeCost(3), MyTurnCondition.instance
        );
        this.addAbility(ability.withFlavorWord("Mental Organism"));

        // Designed Only for Killing -- Creatures your opponents control get -1/-1.
        Ability ability2 = new SimpleStaticAbility(new BoostAllEffect(
            -1, -1,
            Duration.WhileOnBattlefield,
            StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES,
            false
        ));
        this.addAbility(ability2.withFlavorWord("Designed Only for Killing"));
    }

    private MODOK(final MODOK card) {
        super(card);
    }

    @Override
    public MODOK copy() {
        return new MODOK(this);
    }
}
