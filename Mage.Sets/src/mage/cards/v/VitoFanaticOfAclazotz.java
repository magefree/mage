package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.VampireDemonToken;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VitoFanaticOfAclazotz extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public VitoFanaticOfAclazotz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you sacrifice another permanent, you gain 2 life if this is the first time this ability has resolved this turn. If it's the second time, each opponent loses 2 life. If it's the third time, create a 4/3 white and black Vampire Demon creature token with flying.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new IfAbilityHasResolvedXTimesEffect(
                        Outcome.GainLife, 1, new GainLifeEffect(2)
                ).setText("you gain 2 life if this is the first time this ability has resolved this turn"),
                filter
        );
        ability.addEffect(
                new IfAbilityHasResolvedXTimesEffect(
                        Outcome.Damage, 2, new LoseLifeOpponentsEffect(2)
                ).setText("If it's the second time, each opponent loses 2 life")
        );
        ability.addEffect(
                new IfAbilityHasResolvedXTimesEffect(
                        Outcome.PutCreatureInPlay, 3, new CreateTokenEffect(new VampireDemonToken())
                ).setText("If it's the third time, create a 4/3 white and black Vampire Demon creature token with flying")
        );
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private VitoFanaticOfAclazotz(final VitoFanaticOfAclazotz card) {
        super(card);
    }

    @Override
    public VitoFanaticOfAclazotz copy() {
        return new VitoFanaticOfAclazotz(this);
    }
}
