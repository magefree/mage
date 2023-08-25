package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Xanderhall
 */
public final class TerritorialWitchstalker extends CardImpl {

    public TerritorialWitchstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // At the beginning of combat on your turn, if you control a creature with power 4 or greater, Territorial Witchstalker gets +1/+0 until end of turn and can attack this turn as though it didn't have defender.
        Ability ability = new BeginningOfCombatTriggeredAbility(new ConditionalContinuousEffect(new BoostSourceEffect(1, 0, Duration.EndOfTurn), FerociousCondition.instance, "if you control a creature with power 4 or greater, {this} gets +1/+0 until end of turn"), TargetController.YOU, false);
        ability.addEffect(new ConditionalAsThoughEffect(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn), FerociousCondition.instance).setText("and can attack this turn as though it didn't have defender"));
        this.addAbility(ability);

    }

    private TerritorialWitchstalker(final TerritorialWitchstalker card) {
        super(card);
    }

    @Override
    public TerritorialWitchstalker copy() {
        return new TerritorialWitchstalker(this);
    }
}
