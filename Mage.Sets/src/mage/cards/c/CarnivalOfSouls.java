
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Galatolol
 */
public final class CarnivalOfSouls extends CardImpl {

    public CarnivalOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Whenever a creature enters the battlefield, you lose 1 life and add {B}.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(1),
                StaticFilters.FILTER_PERMANENT_A_CREATURE, false, SetTargetPointer.PERMANENT, null, false);
        Effect effect = new AddManaToManaPoolSourceControllerEffect(Mana.BlackMana(1));
        effect.setText("and add {B}.");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CarnivalOfSouls(final CarnivalOfSouls card) {
        super(card);
    }

    @Override
    public CarnivalOfSouls copy() {
        return new CarnivalOfSouls(this);
    }
}
