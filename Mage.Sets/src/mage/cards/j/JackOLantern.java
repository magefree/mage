package mage.cards.j;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JackOLantern extends CardImpl {

    public JackOLantern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, {T}, Sacrifice Jack-o'-Lantern: Exile up to one target card from a graveyard. Draw a card.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);

        // {1}, Exile Jack-o'-Lantern from your graveyard: Add one mana of any color.
        ability = new SimpleManaAbility(Zone.GRAVEYARD, Mana.AnyMana(1), new GenericManaCost(1));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private JackOLantern(final JackOLantern card) {
        super(card);
    }

    @Override
    public JackOLantern copy() {
        return new JackOLantern(this);
    }
}
