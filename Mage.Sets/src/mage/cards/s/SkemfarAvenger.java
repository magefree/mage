package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkemfarAvenger extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("another nontoken Elf or Berserker you control");

    static {
        filter.add(Predicates.or(
                SubType.ELF.getPredicate(),
                SubType.BERSERKER.getPredicate()
        ));
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SkemfarAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever another nontoken Elf or Berserker you control dies, you draw a card and you lose 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you draw a card"), false, filter
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private SkemfarAvenger(final SkemfarAvenger card) {
        super(card);
    }

    @Override
    public SkemfarAvenger copy() {
        return new SkemfarAvenger(this);
    }
}
