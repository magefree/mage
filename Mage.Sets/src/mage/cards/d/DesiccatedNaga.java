package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DesiccatedNaga extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledPlaneswalkerPermanent(SubType.LILIANA, "you control a Liliana planeswalker"));

    public DesiccatedNaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {3}{B}: Target opponent loses 2 life and you gain 2 life. Activate this ability only if you control a Liliana planeswalker.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new LoseLifeTargetEffect(2), new ManaCostsImpl<>("{3}{B}"), condition
        );
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DesiccatedNaga(final DesiccatedNaga card) {
        super(card);
    }

    @Override
    public DesiccatedNaga copy() {
        return new DesiccatedNaga(this);
    }
}
