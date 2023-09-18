package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author TheElk801
 */
public final class SacredWhiteDeer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("you control a Yanggu planeswalker");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.YANGGU.getPredicate());
    }

    public SacredWhiteDeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{G}, {T}: You gain 4 life. Activate this ability only if you control a Yanggu planeswalker.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new GainLifeEffect(4),
                new ManaCostsImpl<>("{3}{G}"),
                new PermanentsOnTheBattlefieldCondition(filter)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SacredWhiteDeer(final SacredWhiteDeer card) {
        super(card);
    }

    @Override
    public SacredWhiteDeer copy() {
        return new SacredWhiteDeer(this);
    }
}
