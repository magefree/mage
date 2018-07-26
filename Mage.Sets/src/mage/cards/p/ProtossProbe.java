package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author NinthWorld
 */
public final class ProtossProbe extends CardImpl {

    public ProtossProbe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{U}, {T}: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{3}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public ProtossProbe(final ProtossProbe card) {
        super(card);
    }

    @Override
    public ProtossProbe copy() {
        return new ProtossProbe(this);
    }
}
