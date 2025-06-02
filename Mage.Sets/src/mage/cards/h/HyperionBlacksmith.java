
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HyperionBlacksmith extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public HyperionBlacksmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: You may tap or untap target artifact an opponent controls.
        Ability ability = new SimpleActivatedAbility(new MayTapOrUntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HyperionBlacksmith(final HyperionBlacksmith card) {
        super(card);
    }

    @Override
    public HyperionBlacksmith copy() {
        return new HyperionBlacksmith(this);
    }
}
