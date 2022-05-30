
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author TheElk801
 */
public final class DeadeyeTracker extends CardImpl {

    public DeadeyeTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{B}, {T}: Exile two target cards from an opponent's graveyard. Deadeye Tracker explores.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new TapSourceCost());
        ability.addCost(new ManaCostsImpl<>("{1}{B}"));
        Effect effect = new ExploreSourceEffect();
        effect.setText("{this} explores");
        ability.addEffect(effect);
        ability.addTarget(new TargetCardInOpponentsGraveyard(2, 2, new FilterCard("cards from an opponent's graveyard"), true));
        this.addAbility(ability);
    }

    private DeadeyeTracker(final DeadeyeTracker card) {
        super(card);
    }

    @Override
    public DeadeyeTracker copy() {
        return new DeadeyeTracker(this);
    }
}
