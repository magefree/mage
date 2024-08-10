package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodrootApothecary extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a noncreature token");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(TokenPredicate.TRUE);
    }

    public BloodrootApothecary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Toxic 2
        this.addAbility(new ToxicAbility(2));

        // When Bloodroot Apothecary enters, you and target opponent each create a Treasure token.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()).setText("you")
        );
        ability.addEffect(new CreateTokenTargetEffect(new TreasureToken())
                .setText("and target opponent each create a Treasure token"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever an opponent sacrifices a noncreature token, that player gets two poison counters.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                Zone.BATTLEFIELD, new AddPoisonCounterTargetEffect(2)
                .setText("that player gets two poison counters"), filter,
                TargetController.OPPONENT, SetTargetPointer.PLAYER, false
        ));
    }

    private BloodrootApothecary(final BloodrootApothecary card) {
        super(card);
    }

    @Override
    public BloodrootApothecary copy() {
        return new BloodrootApothecary(this);
    }
}
