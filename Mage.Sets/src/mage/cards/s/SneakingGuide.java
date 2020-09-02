package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class SneakingGuide extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public SneakingGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}, {T}: Target creature with power 2 or less can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(Duration.EndOfTurn), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SneakingGuide(final SneakingGuide card) {
        super(card);
    }

    @Override
    public SneakingGuide copy() {
        return new SneakingGuide(this);
    }
}
