
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.DamagedPlayerThisTurnPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class GiltspireAvenger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that dealt damage to you this turn");

    static {
        filter.add(new DamagedPlayerThisTurnPredicate(TargetController.YOU));
    }

    public GiltspireAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        this.addAbility(new ExaltedAbility());

        // {T}: Destroy target creature that dealt damage to you this turn.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

    }

    private GiltspireAvenger(final GiltspireAvenger card) {
        super(card);
    }

    @Override
    public GiltspireAvenger copy() {
        return new GiltspireAvenger(this);
    }
}
