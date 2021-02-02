
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CantBeTargetedCardsGraveyardsEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class SilentGravestone extends CardImpl {

    public SilentGravestone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Cards in graveyards can't be the targets of spells or abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedCardsGraveyardsEffect()));

        // {4}, {t}: Exile Silent Gravestone and all cards from all graveyards. Draw a card.
        Ability ability = new SimpleActivatedAbility(new ExileSourceEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ExileGraveyardAllPlayersEffect().setText("and all cards from all graveyards"));
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private SilentGravestone(final SilentGravestone card) {
        super(card);
    }

    @Override
    public SilentGravestone copy() {
        return new SilentGravestone(this);
    }
}
