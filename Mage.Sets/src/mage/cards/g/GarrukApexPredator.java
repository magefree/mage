
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.command.emblems.GarrukApexPredatorEmblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GarrukApexPredatorBeastToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class GarrukApexPredator extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target planeswalker");

    static {
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new AnotherPredicate());
    }

    public GarrukApexPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{5}{B}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

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
        effect.setText("Target opponent gets an emblem with \"Whenever a creature attacks you, it gets +5/+5 and gains trample until end of turn.\"");
        ability = new LoyaltyAbility(effect, -8);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public GarrukApexPredator(final GarrukApexPredator card) {
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
        Permanent creature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (player != null && creature != null) {
            player.gainLife(creature.getToughness().getValue(), game, source);
            return true;
        }
        return false;
    }
}
