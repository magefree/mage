package mage.cards.c;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTop3MayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTargets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author htrajan
 */
public final class ChandraHeartOfFire extends CardImpl {

    public ChandraHeartOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{R}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // +1: Discard your hand, then exile the top three cards of your library. Until end of turn, you may play cards exiled this way.
        Ability ability = new LoyaltyAbility(new DiscardHandControllerEffect(), 1);
        ability.addEffect(new ExileTop3MayPlayUntilEndOfTurnEffect() {{
            setText(", then " + getText(null));
        }});
        this.addAbility(ability);

        // +1: Chandra, Heart of Fire deals 2 damage to any target.
        Ability damageAbility = new LoyaltyAbility(new DamageTargetEffect(2), 1);
        damageAbility.addTarget(new TargetAnyTarget());
        this.addAbility(damageAbility);

        // −9: Search your graveyard and library for any number of red instant and/or sorcery spells, exile them, then shuffle your library. You may cast them this turn. Add six {R}.
        Ability ultimateAbility = new LoyaltyAbility(new ChandraHeartOfFireUltimateEffect(), -9);
        ultimateAbility.addEffect(new BasicManaEffect(Mana.RedMana(6)).setText("Add six {R}"));
        this.addAbility(ultimateAbility);
    }

    private ChandraHeartOfFire(final ChandraHeartOfFire card) {
        super(card);
    }

    @Override
    public ChandraHeartOfFire copy() {
        return new ChandraHeartOfFire(this);
    }
}

class ChandraHeartOfFireUltimateEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    ChandraHeartOfFireUltimateEffect() {
        super(Outcome.Benefit);
        staticText = "Search your graveyard and library for any number of red instant and/or sorcery spells, exile them, then shuffle your library. You may cast them this turn";
    }

    private ChandraHeartOfFireUltimateEffect(ChandraHeartOfFireUltimateEffect effect) {
        super(effect);
    }

    @Override
    public ChandraHeartOfFireUltimateEffect copy() {
        return new ChandraHeartOfFireUltimateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> exiledCards = new HashSet<>();

            filter.setMessage("red instant or sorcery in your graveyard");
            TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.GRAVEYARD, filter);
            if (controller.choose(Outcome.Exile, controller.getGraveyard(), target, game)) {
                List<UUID> targets = target.getTargets();
                controller.moveCards(new CardsImpl(targets), Zone.EXILED, source, game);
                exiledCards.addAll(targets.stream().map(game::getCard).collect(Collectors.toList()));
            }

            filter.setMessage("red instant or sorcery in your library");
            Cards cardsInLibrary = new CardsImpl();
            cardsInLibrary.addAll(controller.getLibrary().getCards(game));
            target = new TargetCardInLibrary(0, Integer.MAX_VALUE, filter);
            if (controller.choose(Outcome.Exile, cardsInLibrary, target, game)) {
                List<UUID> targets = target.getTargets();
                controller.moveCards(new CardsImpl(targets), Zone.EXILED, source, game);
                exiledCards.addAll(targets.stream().map(game::getCard).collect(Collectors.toList()));
            }
            controller.shuffleLibrary(source, game);

            exiledCards.removeIf(card -> !Zone.EXILED.equals(game.getState().getZone(card.getId())));

            if (!exiledCards.isEmpty()) {
                ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTargets(exiledCards, game));
                game.addEffect(effect, source);
            }

            return true;
        }
        return false;
    }

}