package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class ScarletWitchChaoticAvenger extends CardImpl {

    public ScarletWitchChaoticAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WARLOCK);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Scarlet Witch deals combat damage to a player, look at the top two cards of your library, then exile them face down. Then you may cast a Hero or noncreature spell from among cards exiled with Scarlet Witch without paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ScarletWitchChaoticAvengerEffect(), false));
    }

    private ScarletWitchChaoticAvenger(final ScarletWitchChaoticAvenger card) {
        super(card);
    }

    @Override
    public ScarletWitchChaoticAvenger copy() {
        return new ScarletWitchChaoticAvenger(this);
    }
}

class ScarletWitchChaoticAvengerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Hero or noncreature spell");

    static {
        filter.add(Predicates.or(
                Predicates.not(CardType.CREATURE.getPredicate()),
                SubType.HERO.getPredicate()
        ));
    }

    ScarletWitchChaoticAvengerEffect() {
        super(Outcome.PlayForFree);
        staticText = "look at the top two cards of your library, then exile them face down. "
                + "Then you may cast a Hero or noncreature spell from among cards exiled with {this} without paying its mana cost";
    }

    private ScarletWitchChaoticAvengerEffect(final ScarletWitchChaoticAvengerEffect effect) {
        super(effect);
    }

    @Override
    public ScarletWitchChaoticAvengerEffect copy() {
        return new ScarletWitchChaoticAvengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        UUID exileId = CardUtil.getExileZoneId(game, source);
        Cards topCards = new CardsImpl(controller.getLibrary().getTopCards(game, 2));
        if (!topCards.isEmpty()) {
            controller.lookAtCards(source, null, topCards, game);
            controller.moveCardsToExile(
                    topCards.getCards(game), source, game, false,
                    exileId, CardUtil.getSourceName(game, source)
            );
            topCards.retainZone(Zone.EXILED, game);
            for (Card card : topCards.getCards(game)) {
                card.setFaceDown(true, game);
                ContinuousEffect effect = new MayLookAtTargetCardEffect(controller.getId());
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
            }
        }

        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null || exileZone.isEmpty()) {
            return true;
        }
        return CardUtil.castSpellWithAttributesForFree(controller, source, game, new CardsImpl(exileZone), filter);
    }
}
