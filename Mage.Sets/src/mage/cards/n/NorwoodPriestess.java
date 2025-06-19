package mage.cards.n;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class NorwoodPriestess extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("a green creature card");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public NorwoodPriestess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: You may put a green creature card from your hand onto the battlefield. Activate this ability only during your turn, before attackers are declared.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new PutCardFromHandOntoBattlefieldEffect(filter),
                new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance
        ));
    }

    private NorwoodPriestess(final NorwoodPriestess card) {
        super(card);
    }

    @Override
    public NorwoodPriestess copy() {
        return new NorwoodPriestess(this);
    }
}
