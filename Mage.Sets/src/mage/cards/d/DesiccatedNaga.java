
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class DesiccatedNaga extends CardImpl {

    private static final FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("you control a Liliana planeswalker");

    static {
        filter.add(new SubtypePredicate(SubType.LILIANA));
    }

    public DesiccatedNaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.NAGA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {3}{B}: Target opponent loses 2 life and you gain 2 life. Activate this ability only if you control a Liliana planeswalker.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new LoseLifeTargetEffect(2),
                new ManaCostsImpl("{3}{B}"),
                new PermanentsOnTheBattlefieldCondition(filter));
        ability.addTarget(new TargetOpponent());
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public DesiccatedNaga(final DesiccatedNaga card) {
        super(card);
    }

    @Override
    public DesiccatedNaga copy() {
        return new DesiccatedNaga(this);
    }
}
