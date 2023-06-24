package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.command.emblems.GarrukApexPredatorEmblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GarrukApexPredatorBeastToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GarrukApexPredator extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target planeswalker");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public GarrukApexPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{5}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);

        this.setStartingLoyalty(5);

        // +1: Destroy another target planeswalker.
        LoyaltyAbility ability = new LoyaltyAbility(new DestroyTargetEffect(), 1);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // +1: Create a 3/3 black Beast creature token with deathtouch.
        ability = new LoyaltyAbility(new CreateTokenEffect(new GarrukApexPredatorBeastToken()), 1);
        this.addAbility(ability);

        // -3: Destroy target creature. You gain life equal to its toughness.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addEffect(new GarrukApexPredatorEffect3());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -8: Target opponent gets an emblem with "Whenever a creature attacks you, it gets +5/+5 and gains trample until end of turn."
        Effect effect = new GetEmblemTargetPlayerEffect(new GarrukApexPredatorEmblem());
        ability = new LoyaltyAbility(effect, -8);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private GarrukApexPredator(final GarrukApexPredator card) {
        super(card);
    }

    @Override
    public GarrukApexPredator copy() {
        return new GarrukApexPredator(this);
    }
}

class GarrukApexPredatorEffect3 extends OneShotEffect {

    public GarrukApexPredatorEffect3() {
        super(Outcome.GainLife);
        this.staticText = "You gain life equal to its toughness";
    }

    public GarrukApexPredatorEffect3(final GarrukApexPredatorEffect3 effect) {
        super(effect);
    }

    @Override
    public GarrukApexPredatorEffect3 copy() {
        return new GarrukApexPredatorEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (player != null && creature != null) {
            player.gainLife(creature.getToughness().getValue(), game, source);
            return true;
        }
        return false;
    }
}
