package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

public class JinGitaxias extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a noncreature spell with mana value 3 or greater");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public JinGitaxias(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.PRAETOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.t.TheGreatSynthesis.class;

        //Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        //Whenever you cast a noncreature spell with mana value 3 or greater, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                filter, false
        ));

        //{3}{U}: Exile Jin-Gitaxias, then return it to the battlefield transformed under its owner’s control. Activate
        //only as a sorcery and only if you have seven or more cards in hand.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{3}{U}"),
                new CardsInHandCondition(ComparisonType.MORE_THAN, 6)
        ).setTiming(TimingRule.SORCERY));
    }

    private JinGitaxias(final JinGitaxias card) {
        super(card);
    }

    @Override
    public JinGitaxias copy() {
        return new JinGitaxias(this);
    }
}
