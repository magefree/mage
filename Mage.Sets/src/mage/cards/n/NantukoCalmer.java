
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LoneFox
 */
public final class NantukoCalmer extends CardImpl {

    public NantukoCalmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {G}, {tap}, Sacrifice Nantuko Calmer: Destroy target enchantment.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
        // Threshold - Nantuko Calmer gets +1/+1 as long as seven or more cards are in your graveyard.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), new CardsInControllerGraveyardCondition(7),
            "{this} gets +1/+1 as long as seven or more cards are in your graveyard"));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private NantukoCalmer(final NantukoCalmer card) {
        super(card);
    }

    @Override
    public NantukoCalmer copy() {
        return new NantukoCalmer(this);
    }
}
