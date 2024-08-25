package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class EtrataDeadlyFugitive extends CardImpl {
    private static final FilterPermanent filterAssassin = new FilterControlledPermanent("an Assassin you control");
    private static final FilterPermanent filterFaceDown = new FilterControlledPermanent("Face-down creatures you control");

    static {
        filterAssassin.add(SubType.ASSASSIN.getPredicate());
        filterFaceDown.add(FaceDownPredicate.instance);
    }

    public EtrataDeadlyFugitive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Face-down creatures you control have "{2}{U}{B}: Turn this creature face up. If you can't, exile it, then you may cast the exiled card without paying its mana cost."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(new SimpleActivatedAbility(
                new EtrataFlipEffect(), new ManaCostsImpl<>("{2}{U}{B}")), Duration.WhileOnBattlefield, filterFaceDown)));

        // Whenever an Assassin you control deals combat damage to an opponent, cloak the top card of that player's library.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(new EtrataCloakEffect(), filterAssassin,
                false, SetTargetPointer.NONE, true, true, TargetController.OPPONENT));
    }

    private EtrataDeadlyFugitive(final EtrataDeadlyFugitive card) {
        super(card);
    }

    @Override
    public EtrataDeadlyFugitive copy() {
        return new EtrataDeadlyFugitive(this);
    }
}

class EtrataCloakEffect extends OneShotEffect {
    EtrataCloakEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "cloak the top card of that player's library";
    }

    private EtrataCloakEffect(final EtrataCloakEffect effect) {
        super(effect);
    }

    @Override
    public EtrataCloakEffect copy() {
        return new EtrataCloakEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        List<Permanent> cloakedList = ManifestEffect.doManifestCards(game, source, controller, opponent.getLibrary().getTopCards(game, 1), true);
        return !cloakedList.isEmpty();
    }
}

class EtrataFlipEffect extends OneShotEffect {

    EtrataFlipEffect() {
        super(Outcome.Benefit);
        staticText = "Turn this creature face up. If you can't, exile it, then you may cast the exiled card without paying its mana cost.";
    }

    private EtrataFlipEffect(final EtrataFlipEffect effect) {
        super(effect);
    }

    @Override
    public EtrataFlipEffect copy() {
        return new EtrataFlipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent != null && player != null) {
            boolean success = permanent.turnFaceUp(source, game, source.getControllerId());
            if (!success) {
                Card card = permanent.getMainCard();
                player.moveCards(card, Zone.EXILED, source, game);
                game.processAction();
                CardUtil.castSpellWithAttributesForFree(player, source, game, card);
                // Not sure why, but the client asks if you want to cast a face-down creature instead of the actual spell
                // It plays correctly and casts the instant/sorcery spell anyway
            }
        }
        return false;
    }
}
