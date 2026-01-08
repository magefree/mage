package mage.cards.m;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardIntoPlayWithHasteAndSacrificeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeekAttack extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with total power and toughness 5 or less");

    static {
        filter.add(MeekAttackPredicate.instance);
    }

    public MeekAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // {1}{R}: You may put a creature card with total power and toughness 5 or less from your hand onto the battlefield. That creature gains haste. At the beginning of the next end step, sacrifice that creature.
        this.addAbility(new SimpleActivatedAbility(new PutCardIntoPlayWithHasteAndSacrificeEffect(
                filter, Duration.Custom, "That creature", "the creature"
        ), new ManaCostsImpl<>("{1}{R}")));
    }

    private MeekAttack(final MeekAttack card) {
        super(card);
    }

    @Override
    public MeekAttack copy() {
        return new MeekAttack(this);
    }
}

enum MeekAttackPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return input.getToughness().getValue() + input.getPower().getValue() <= 5;
    }
}
