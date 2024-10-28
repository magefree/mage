package mage.cards.e;

import mage.Mana;
import mage.abilities.common.BeginningOfFirstMainTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EladamrisVineyard extends CardImpl {

    public EladamrisVineyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // At the beginning of each player's precombat main phase, add {G}{G} to that player's mana pool.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
                new AddManaToManaPoolTargetControllerEffect(Mana.GreenMana(2), "that player's"),
                TargetController.EACH_PLAYER, false));
    }

    private EladamrisVineyard(final EladamrisVineyard card) {
        super(card);
    }

    @Override
    public EladamrisVineyard copy() {
        return new EladamrisVineyard(this);
    }
}
