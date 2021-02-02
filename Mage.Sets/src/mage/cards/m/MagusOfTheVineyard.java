
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class MagusOfTheVineyard extends CardImpl {

    public MagusOfTheVineyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of each player's precombat main phase, add {G}{G} to that player's mana pool.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                Zone.BATTLEFIELD, new AddManaToManaPoolTargetControllerEffect(Mana.GreenMana(2), "that player's"), TargetController.ANY, false, true));
    }

    private MagusOfTheVineyard(final MagusOfTheVineyard card) {
        super(card);
    }

    @Override
    public MagusOfTheVineyard copy() {
        return new MagusOfTheVineyard(this);
    }
}
