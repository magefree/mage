package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SacredWhiteDeer extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPlaneswalkerPermanent(SubType.YANGGU, "you control a Yanggu planeswalker")
    );

    public SacredWhiteDeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{G}, {T}: You gain 4 life. Activate this ability only if you control a Yanggu planeswalker.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new GainLifeEffect(4), new ManaCostsImpl<>("{3}{G}"), condition
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
