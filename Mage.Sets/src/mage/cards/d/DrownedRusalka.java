
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DrownedRusalka extends CardImpl {

    public DrownedRusalka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U}, Sacrifice a creature: Discard a card, then draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DiscardControllerEffect(1), new ManaCostsImpl<>("{U}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private DrownedRusalka(final DrownedRusalka card) {
        super(card);
    }

    @Override
    public DrownedRusalka copy() {
        return new DrownedRusalka(this);
    }
}
