package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Neutrino
 */
public final class GandalfGoblinsBane extends AdventureCard {

    private static final FilterSpell filter = new FilterSpell("a noncreature spell");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public GandalfGoblinsBane(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId,
                setInfo,
                new CardType[]{CardType.CREATURE},
                new CardType[]{CardType.SORCERY},
                "{2}{R}",
                "Flameshape",
                "{1}{R}"
        );

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell,
        // Gandalf gets +1/+1 until end of turn
        // and deals 1 damage to each opponent.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                filter,
                false
        );

        ability.addEffect(
                new DamagePlayersEffect(1, TargetController.OPPONENT)
                        .concatBy("and")
        );

        this.addAbility(ability);

        // Flameshape
        this.getSpellCard()
                .getSpellAbility()
                .addEffect(new FlameshapeEffect());

        this.finalizeAdventure();
    }

    private GandalfGoblinsBane(final GandalfGoblinsBane card) {
        super(card);
    }

    @Override
    public GandalfGoblinsBane copy() {
        return new GandalfGoblinsBane(this);
    }
}


class FlameshapeEffect extends OneShotEffect {

    FlameshapeEffect() {
        super(Outcome.Benefit);
        this.staticText =
                "look at the top two cards of your library and exile them face down. "
                        + "For as long as they remain exiled, you may play them "
                        + "if you control a Wizard";
    }

    private FlameshapeEffect(final FlameshapeEffect effect) {
        super(effect);
    }

    @Override
    public FlameshapeEffect copy() {
        return new FlameshapeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) {
            return false;
        }

        Cards cards = new CardsImpl(
                controller.getLibrary().getTopCards(game, 2)
        );

        if (cards.isEmpty()) {
            return true;
        }

        UUID exileZoneId = CardUtil.getExileZoneId(
                game,
                source.getSourceId(),
                source.getStackMomentSourceZCC()
        );

        MageObject sourceObject = source.getSourceObject(game);

        String exileName =
                sourceObject == null
                        ? "Flameshape"
                        : sourceObject.getIdName();

        Set<Card> exiledCards = new HashSet<>();

        for (Card card : cards.getCards(game)) {

            card.setFaceDown(true, game);

            if (controller.moveCardsToExile(
                    card,
                    source,
                    game,
                    false,
                    exileZoneId,
                    exileName
            )) {

                card.setFaceDown(true, game);

                exiledCards.add(card);

                ContinuousEffect lookEffect =
                        new MayLookAtTargetCardEffect(controller.getId());

                lookEffect.setTargetPointer(
                        new FixedTarget(card.getId(), game)
                );

                game.addEffect(lookEffect, source);
            }
        }

        if (!exiledCards.isEmpty()) {

            ContinuousEffect playEffect =
                    new FlameshapePlayFromExileEffect();

            playEffect.setTargetPointer(
                    new FixedTargets(exiledCards, game)
            );

            game.addEffect(playEffect, source);
        }

        return true;
    }
}


class FlameshapePlayFromExileEffect
        extends PlayFromNotOwnHandZoneTargetEffect {

    private static final FilterControlledCreaturePermanent wizardFilter =
            new FilterControlledCreaturePermanent(
                    SubType.WIZARD,
                    "Wizard you control"
            );

    FlameshapePlayFromExileEffect() {
        super(
                Zone.EXILED,
                TargetController.YOU,
                Duration.Custom,
                false,
                false
        );
    }

    private FlameshapePlayFromExileEffect(
            final FlameshapePlayFromExileEffect effect
    ) {
        super(effect);
    }

    @Override
    public FlameshapePlayFromExileEffect copy() {
        return new FlameshapePlayFromExileEffect(this);
    }

    private boolean controlsWizard(Game game, Ability source) {

        return game.getBattlefield().containsControlled(
                wizardFilter,
                source.getControllerId(),
                source,
                game,
                1
        );
    }

    @Override
    public boolean applies(
            UUID objectId,
            Ability source,
            UUID affectedControllerId,
            Game game
    ) {

        if (!controlsWizard(game, source)) {
            return false;
        }

        return super.applies(
                objectId,
                source,
                affectedControllerId,
                game
        );
    }

    @Override
    public boolean applies(
            UUID objectId,
            Ability affectedAbility,
            Ability source,
            Game game,
            UUID playerId
    ) {

        if (!controlsWizard(game, source)) {
            return false;
        }

        return super.applies(
                objectId,
                affectedAbility,
                source,
                game,
                playerId
        );
    }
}
