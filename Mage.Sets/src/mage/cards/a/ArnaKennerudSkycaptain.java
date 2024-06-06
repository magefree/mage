package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class ArnaKennerudSkycaptain extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("a modified creature you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public ArnaKennerudSkycaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Ward--Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost()));

        // Whenever a modified creature you control attacks, double the number of each kind of counter on it. Then for each nontoken permanent attached to it, create a token that's a copy of that permanent attached to that creature.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new ArnaKennerudSkycaptainTargetEffect(), false, filter, true
        ));
    }

    private ArnaKennerudSkycaptain(final ArnaKennerudSkycaptain card) {
        super(card);
    }

    @Override
    public ArnaKennerudSkycaptain copy() {
        return new ArnaKennerudSkycaptain(this);
    }
}

class ArnaKennerudSkycaptainTargetEffect extends OneShotEffect {

    ArnaKennerudSkycaptainTargetEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of each kind of counter on it. Then for each nontoken permanent attached to it, create a token that's a copy of that permanent attached to that creature";
    }

    private ArnaKennerudSkycaptainTargetEffect(final ArnaKennerudSkycaptainTargetEffect effect) {
        super(effect);
    }

    @Override
    public ArnaKennerudSkycaptainTargetEffect copy() {
        return new ArnaKennerudSkycaptainTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Counters counters = permanent.getCounters(game).copy();
        for (Counter counter : counters.values()) {
            permanent.addCounters(counter, source, game);
        }
        Set<Permanent> attachments = permanent
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(p -> !(p instanceof PermanentToken))
                .collect(Collectors.toSet());
        for (Permanent attachment : attachments) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
            effect.setSavedPermanent(attachment);
            effect.setAttachedTo(permanent.getId());
            effect.apply(game, source);
        }
        return true;
    }
}
