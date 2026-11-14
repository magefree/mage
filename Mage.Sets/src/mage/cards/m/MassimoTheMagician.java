package mage.cards.m;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author alexchet
 */
public final class MassimoTheMagician extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card with mana value 1 from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 1));
    }

    public MassimoTheMagician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Massimo or another creature you control enters, exile up to one target instant or sorcery card with mana value 1 from your graveyard. If a card is exiled this way, that creature gains "Whenever this creature deals combat damage to a player, copy the exiled card. You may cast the copy without paying its mana cost."
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new MassimoTheMagicianExileEffect(), StaticFilters.FILTER_PERMANENT_CREATURE,
                false, SetTargetPointer.PERMANENT, true
        );
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private MassimoTheMagician(final MassimoTheMagician card) {
        super(card);
    }

    @Override
    public MassimoTheMagician copy() {
        return new MassimoTheMagician(this);
    }
}

class MassimoTheMagicianExileEffect extends OneShotEffect {

    MassimoTheMagicianExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target instant or sorcery card with mana value 1 from your graveyard. " +
                "If a card is exiled this way, that creature gains \"Whenever this creature deals combat damage " +
                "to a player, copy the exiled card. You may cast the copy without paying its mana cost.\"";
    }

    private MassimoTheMagicianExileEffect(final MassimoTheMagicianExileEffect effect) {
        super(effect);
    }

    @Override
    public MassimoTheMagicianExileEffect copy() {
        return new MassimoTheMagicianExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (game.getState().getZone(card.getId()) != Zone.EXILED) {
            return true;
        }
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature == null) {
            return true;
        }
        game.addEffect(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(new MassimoTheMagicianCastEffect(card, game)),
                Duration.Custom
        ).setTargetPointer(new FixedTarget(creature, game)), source);
        return true;
    }
}

class MassimoTheMagicianCastEffect extends OneShotEffect {

    private final MageObjectReference mor;

    MassimoTheMagicianCastEffect(Card card, Game game) {
        super(Outcome.PlayForFree);
        this.mor = new MageObjectReference(card, game);
        staticText = "copy the exiled card. You may cast the copy without paying its mana cost";
    }

    private MassimoTheMagicianCastEffect(final MassimoTheMagicianCastEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public MassimoTheMagicianCastEffect copy() {
        return new MassimoTheMagicianCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = mor.getCard(game);
        if (player == null || card == null) {
            return false;
        }
        Card copiedCard = game.copyCard(card, source, player.getId());
        if (copiedCard == null) {
            return false;
        }
        if (!player.chooseUse(outcome, "Cast the copy of " + card.getName() + " without paying its mana cost?", source, game)) {
            return false;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(copiedCard, game, true),
                game, true, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
