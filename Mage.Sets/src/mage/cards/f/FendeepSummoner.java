
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class FendeepSummoner extends CardImpl {

    static final FilterLandPermanent filter = new FilterLandPermanent("Swamp");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public FendeepSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {T}: Up to two target Swamps each become 3/5 Treefolk Warrior creatures in addition to their other types until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(
                new CreatureToken(3, 5, "3/5 Treefolk Warrior", SubType.TREEFOLK, SubType.WARRIOR),
                false, false, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent(0, 2, filter, false));
        this.addAbility(ability);
    }

    private FendeepSummoner(final FendeepSummoner card) {
        super(card);
    }

    @Override
    public FendeepSummoner copy() {
        return new FendeepSummoner(this);
    }
}
