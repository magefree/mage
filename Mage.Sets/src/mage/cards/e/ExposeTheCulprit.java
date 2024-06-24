package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TurnFaceUpTargetEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author notgreat
 */
public final class ExposeTheCulprit extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("face-down creature");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public ExposeTheCulprit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Turn target face-down creature face up.
        this.getSpellAbility().addEffect(new TurnFaceUpTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // * Exile any number of face-up creatures you control with disguise in a face-down pile, shuffle that pile, then cloak them.
        Mode mode = new Mode(new ExposeTheCulpritEffect());
        this.getSpellAbility().addMode(mode);
    }

    private ExposeTheCulprit(final ExposeTheCulprit card) {
        super(card);
    }

    @Override
    public ExposeTheCulprit copy() {
        return new ExposeTheCulprit(this);
    }
}

class ExposeTheCulpritEffect extends OneShotEffect {
    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("face-up creatures you control with disguise");

    static {
        filter.add(Predicates.not(FaceDownPredicate.instance));
        filter.add(new AbilityPredicate(DisguiseAbility.class));
    }

    ExposeTheCulpritEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile any number of face-up creatures you control with disguise in a face-down pile, shuffle that pile, then cloak them.";
    }

    private ExposeTheCulpritEffect(final ExposeTheCulpritEffect effect) {
        super(effect);
    }

    @Override
    public ExposeTheCulpritEffect copy() {
        return new ExposeTheCulpritEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target creatures = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        if (controller.choose(Outcome.Neutral, creatures, source, game)) {
            List<Card> cardsToCloak = creatures.getTargets().stream().map(
                    x -> {
                        Card card = game.getCard(x);
                        if (card != null) {
                            card.setFaceDown(true, game);
                        }
                        return card;
                    }
            ).filter(Objects::nonNull).collect(Collectors.toList());
            if (controller.moveCards(new HashSet<>(cardsToCloak), Zone.EXILED, source, game)) {
                Collections.shuffle(cardsToCloak);
                game.informPlayers(controller.getLogName() + " shuffles the face-down pile");
                game.processAction();
                ManifestEffect.doManifestCards(game, source, controller, new LinkedHashSet<>(cardsToCloak), true);
            }
        }
        return true;
    }
}
