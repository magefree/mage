package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TokkaAndRahzarTerribleTwos extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a spell, if the amount of mana spent to cast it was less than its mana value");

    static {
        filter.add(TokkaAndRahzarTerribleTwosPredicate.instance);
    }

    public TokkaAndRahzarTerribleTwos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/R}{B/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever a player casts a spell, if the amount of mana spent to cast it was less than its mana value, Tokka & Rahzar deal 3 damage to that player.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new DamageTargetEffect(3)
                        .setText("{this} deal 3 damage to that player"),
                filter, false, SetTargetPointer.PLAYER
        ));
    }

    private TokkaAndRahzarTerribleTwos(final TokkaAndRahzarTerribleTwos card) {
        super(card);
    }

    @Override
    public TokkaAndRahzarTerribleTwos copy() {
        return new TokkaAndRahzarTerribleTwos(this);
    }
}

enum TokkaAndRahzarTerribleTwosPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input.getStackAbility().getManaCostsToPay().getUsedManaToPay().count() < input.getManaValue();
    }
}
