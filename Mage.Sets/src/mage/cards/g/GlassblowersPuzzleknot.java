
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */
public final class GlassblowersPuzzleknot extends CardImpl {

    public GlassblowersPuzzleknot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // When Glassblower's Puzzleknot enters the battlefield, scry 2, then you get {E}{E}.
        Effect scryEffect = new ScryEffect(2);
        scryEffect.setText("scry 2");
        Ability ability = new EntersBattlefieldTriggeredAbility(scryEffect);
        Effect energyEffect = new GetEnergyCountersControllerEffect(2);
        energyEffect.setText(", then you get {E}{E}");
        ability.addEffect(energyEffect);
        this.addAbility(ability);

        // {2}{U}, Sacrifice Glassblower's Puzzleknot: Scry 2, then you get {E}{E}.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, scryEffect, new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(energyEffect);
        this.addAbility(ability);
    }

    private GlassblowersPuzzleknot(final GlassblowersPuzzleknot card) {
        super(card);
    }

    @Override
    public GlassblowersPuzzleknot copy() {
        return new GlassblowersPuzzleknot(this);
    }
}
