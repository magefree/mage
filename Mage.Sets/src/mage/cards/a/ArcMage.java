package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author fireshoes
 */
public final class ArcMage extends CardImpl {

    public ArcMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{R}, {tap}, Discard a card: Arc Mage deals 2 damage divided as you choose among one or two targets.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageMultiEffect(2), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetAnyTargetAmount(2));
        this.addAbility(ability);
    }

    private ArcMage(final ArcMage card) {
        super(card);
    }

    @Override
    public ArcMage copy() {
        return new ArcMage(this);
    }
}
