
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class DeepSpawn extends CardImpl {

    public DeepSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}{U}");
        this.subtype.add(SubType.HOMARID);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, sacrifice Deep Spawn unless you put the top two cards of your library into your graveyard.
        Effect effect = new SacrificeSourceUnlessPaysEffect(new MillCardsCost(2));
        effect.setText("sacrifice {this} unless you mill two cards");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false));
        // {U}: Deep Spawn gains shroud until end of turn and doesn't untap during your next untap step. Tap Deep Spawn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(
            ShroudAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{U}"));
        effect = new DontUntapInControllersNextUntapStepSourceEffect();
        effect.setText("and doesn't untap during your next untap step");
        ability.addEffect(effect);
        ability.addEffect(new TapSourceEffect());
        this.addAbility(ability);
    }

    private DeepSpawn(final DeepSpawn card) {
        super(card);
    }

    @Override
    public DeepSpawn copy() {
        return new DeepSpawn(this);
    }
}
