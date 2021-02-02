
package mage.cards.k;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class Kaleidostone extends CardImpl {

    public Kaleidostone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // When Kaleidostone enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
        // {5}, {tap}, Sacrifice Kaleidostone: Add {W}{U}{B}{R}{G}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 1, 1, 1, 1, 0, 0, 0), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private Kaleidostone(final Kaleidostone card) {
        super(card);
    }

    @Override
    public Kaleidostone copy() {
        return new Kaleidostone(this);
    }
}
