package mage.cards.l;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LaviniaFoilToConspiracy extends CardImpl {

    public LaviniaFoilToConspiracy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/U}{W/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast your second spell each turn, investigate.
        this.addAbility(new CastSecondSpellTriggeredAbility(new InvestigateEffect()));

        // {T}: Add {C}{C}. Activate only during an opponent's turn.
        this.addAbility(new LaviniaFoilToConspiracyAbility());
    }

    private LaviniaFoilToConspiracy(final LaviniaFoilToConspiracy card) {
        super(card);
    }

    @Override
    public LaviniaFoilToConspiracy copy() {
        return new LaviniaFoilToConspiracy(this);
    }
}

class LaviniaFoilToConspiracyAbility extends ActivateIfConditionManaAbility {
    LaviniaFoilToConspiracyAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.ColorlessMana(2)), new TapSourceCost(), NotMyTurnCondition.instance);
    }

    private LaviniaFoilToConspiracyAbility(final LaviniaFoilToConspiracyAbility ability) {
        super(ability);
    }

    @Override
    public LaviniaFoilToConspiracyAbility copy() {
        return new LaviniaFoilToConspiracyAbility(this);
    }

    @Override
    public String getRule() {
        return "{T}: Add {C}{C}. Activate only during an opponent's turn.";
    }
}