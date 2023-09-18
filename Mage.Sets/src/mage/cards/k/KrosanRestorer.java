package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class KrosanRestorer extends CardImpl {

    public KrosanRestorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Untap target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // Threshold - {tap}: Untap up to three target lands. Activate this ability only if seven or more cards are in your graveyard.
        ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new UntapTargetEffect(),
                new TapSourceCost(),
                new CardsInControllerGraveyardCondition(7));
        ability.addTarget(new TargetLandPermanent(0, 3));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private KrosanRestorer(final KrosanRestorer card) {
        super(card);
    }

    @Override
    public KrosanRestorer copy() {
        return new KrosanRestorer(this);
    }
}
