
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class NomadStadium extends CardImpl {

    public NomadStadium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {W}. Nomad Stadium deals 1 damage to you.
        Ability manaAbility = new WhiteManaAbility();
        manaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(manaAbility);

        // Threshold - {W}, {tap}, Sacrifice Nomad Stadium: You gain 4 life. Activate this ability only if seven or more cards are in your graveyard.
        Ability thresholdAbility = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
            new GainLifeEffect(4),
            new ManaCostsImpl<>("{W}"),
            new CardsInControllerGraveyardCondition(7));
        thresholdAbility.addCost(new TapSourceCost());
        thresholdAbility.addCost(new SacrificeSourceCost());
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private NomadStadium(final NomadStadium card) {
        super(card);
    }

    @Override
    public NomadStadium copy() {
        return new NomadStadium(this);
    }
}
