
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ShizukoCallerOfAutumn extends CardImpl {

    public ShizukoCallerOfAutumn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of each player's upkeep, that player adds {G}{G}{G}. Until end of turn, this mana doesn't empty from that player's mana pool as steps and phases end.
        Effect effect = new AddManaToManaPoolTargetControllerEffect(Mana.GreenMana(3), "that player", true);
        effect.setText("that player adds {G}{G}{G}. Until end of turn, they don't lose this mana as steps and phases end");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.ANY, false));
        
    }

    private ShizukoCallerOfAutumn(final ShizukoCallerOfAutumn card) {
        super(card);
    }

    @Override
    public ShizukoCallerOfAutumn copy() {
        return new ShizukoCallerOfAutumn(this);
    }
}
