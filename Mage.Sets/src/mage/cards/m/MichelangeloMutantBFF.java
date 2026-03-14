package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.permanent.token.MutagenToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MichelangeloMutantBFF extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public MichelangeloMutantBFF(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Each creature you control with a counter on it can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(
                new CantBeBlockedByMoreThanOneAllEffect(filter)
        ));

        // Whenever Michelangelo enters or attacks, create a Mutagen token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new CreateTokenEffect(new MutagenToken())
        ));
    }

    private MichelangeloMutantBFF(final MichelangeloMutantBFF card) {
        super(card);
    }

    @Override
    public MichelangeloMutantBFF copy() {
        return new MichelangeloMutantBFF(this);
    }
}
