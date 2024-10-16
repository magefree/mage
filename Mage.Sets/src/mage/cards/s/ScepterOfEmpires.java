package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class ScepterOfEmpires extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();
    private static final FilterPermanent filter2 = new FilterControlledArtifactPermanent();

    static {
        filter.add(new NamePredicate("Crown of Empires"));
        filter2.add(new NamePredicate("Throne of Empires"));
    }

    private static final Condition condition = new CompoundCondition(
            new PermanentsOnTheBattlefieldCondition(filter),
            new PermanentsOnTheBattlefieldCondition(filter2)
    );

    public ScepterOfEmpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {tap}: Scepter of Empires deals 1 damage to target player. It deals 3 damage to that player instead if you control artifacts named Crown of Empires and Throne of Empires.
        Ability ability = new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new DamageTargetEffect(3), new DamageTargetEffect(1), condition, "{this} deals " +
                "1 damage to target player. It deals 3 damage to that player instead if " +
                "you control artifacts named Crown of Empires and Throne of Empires"
        ), new TapSourceCost());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private ScepterOfEmpires(final ScepterOfEmpires card) {
        super(card);
    }

    @Override
    public ScepterOfEmpires copy() {
        return new ScepterOfEmpires(this);
    }
}
