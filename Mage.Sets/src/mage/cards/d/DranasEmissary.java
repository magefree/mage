
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class DranasEmissary extends CardImpl {

    public DranasEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // At the beginning of your upkeep, each opponent loses 1 life and you gain 1 life.
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new LoseLifeOpponentsEffect(1), TargetController.YOU, false);
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private DranasEmissary(final DranasEmissary card) {
        super(card);
    }

    @Override
    public DranasEmissary copy() {
        return new DranasEmissary(this);
    }
}
