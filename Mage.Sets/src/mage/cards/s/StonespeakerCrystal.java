package mage.cards.s;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StonespeakerCrystal extends CardImpl {

    public StonespeakerCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {T}: Add {C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));

        // {2}, {T}, Sacrifice Stonespeaker Crystal: Exile any number of target players' graveyards. Draw a card.
        Ability ability = new SimpleActivatedAbility(new ExileGraveyardAllTargetPlayerEffect(), new GenericManaCost(2));
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);
    }

    private StonespeakerCrystal(final StonespeakerCrystal card) {
        super(card);
    }

    @Override
    public StonespeakerCrystal copy() {
        return new StonespeakerCrystal(this);
    }
}
