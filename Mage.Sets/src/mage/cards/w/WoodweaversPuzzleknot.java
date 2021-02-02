
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */
public final class WoodweaversPuzzleknot extends CardImpl {

    public WoodweaversPuzzleknot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // When Woodweaver's Puzzleknot enters the battlefield, you gain 3 life and get {E}{E}{E}.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3));
        Effect effect = new GetEnergyCountersControllerEffect(3);
        effect.setText("and get {E}{E}{E}");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {2}{G}, Sacrifice Woodweaver's Puzzleknot: You gain 3 life and get {E}{E}{E}.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private WoodweaversPuzzleknot(final WoodweaversPuzzleknot card) {
        super(card);
    }

    @Override
    public WoodweaversPuzzleknot copy() {
        return new WoodweaversPuzzleknot(this);
    }
}
