package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowTheHedgehog extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with flash or haste");
    private static final FilterNonlandCard filter2 = new FilterNonlandCard();

    static {
        filter.add(Predicates.or(
                new AbilityPredicate(FlashAbility.class),
                new AbilityPredicate(HasteAbility.class)
        ));
        filter2.add(ShadowTheHedgehogPredicate.instance);
    }

    public ShadowTheHedgehog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HEDGEHOG);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Shadow the Hedgehog or another creature you control with flash or haste dies, draw a card.
        this.addAbility(new DiesThisOrAnotherTriggeredAbility(new DrawCardSourceControllerEffect(1), false, filter));

        // Chaos Control -- Each spell you cast has split second if mana from an artifact was spent to cast it.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new SplitSecondAbility(), filter2)
                .setText("each spell you cast has split second if mana from an artifact was spent to cast it")).withFlavorWord("Chaos Control"));
    }

    private ShadowTheHedgehog(final ShadowTheHedgehog card) {
        super(card);
    }

    @Override
    public ShadowTheHedgehog copy() {
        return new ShadowTheHedgehog(this);
    }
}

enum ShadowTheHedgehogPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return input instanceof Spell && ManaPaidSourceWatcher.getArtifactPaid(input.getId(), game) > 0;
    }
}
