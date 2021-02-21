package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Slitherwisp extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("another spell that has flash");

    static {
        filter.add(new AbilityPredicate(FlashAbility.class));
        filter.add(AnotherPredicate.instance);
    }

    public Slitherwisp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{B}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever you cast another spell with flash, you draw a card and each opponent loses 1 life.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
                        .setText("you draw a card"),
                filter, false
        );
        ability.addEffect(new LoseLifeOpponentsEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private Slitherwisp(final Slitherwisp card) {
        super(card);
    }

    @Override
    public Slitherwisp copy() {
        return new Slitherwisp(this);
    }
}
