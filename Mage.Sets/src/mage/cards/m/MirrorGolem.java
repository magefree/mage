package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author noahg
 */
public final class MirrorGolem extends CardImpl {

    public MirrorGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Imprint - When Mirror Golem enters the battlefield, you may exile target card from a graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MirrorGolemImprintEffect(), true, "Imprint &mdash; ");
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // Mirror Golem has protection from each of the exiled card's card types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MirrorGolemEffect()));
    }

    public MirrorGolem(final MirrorGolem card) {
        super(card);
    }

    @Override
    public MirrorGolem copy() {
        return new MirrorGolem(this);
    }
}

class MirrorGolemImprintEffect extends OneShotEffect {

    MirrorGolemImprintEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile target card from a graveyard";
    }

    MirrorGolemImprintEffect(final MirrorGolemImprintEffect effect) {
        super(effect);
    }

    @Override
    public MirrorGolemImprintEffect copy() {
        return new MirrorGolemImprintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.moveCardsToExile(card, source, game, true, CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), source.getSourceObject(game).getIdName());
                if (sourcePermanent != null) {
                    sourcePermanent.imprint(this.getTargetPointer().getFirst(game, source), game);
                }
            }
            return true;
        }
        return false;
    }
}

class MirrorGolemEffect extends ContinuousEffectImpl {

    public MirrorGolemEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.addDependedToType(DependencyType.AddingAbility);
        staticText = "{this} has protection from each of the exiled card's card types.";
    }

    public MirrorGolemEffect(final MirrorGolemEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());

        if (sourceObject == null || sourceObject.getImprinted() == null) {
            return false;
        }

        for (UUID imprinted : sourceObject.getImprinted()){
            if (imprinted != null){
                Card card = game.getCard(imprinted);
                if (card != null) {
                    for (CardType cardType : card.getCardType()){
                        FilterCard filterCard;
                        if (cardType.equals(CardType.SORCERY)){
                            filterCard = new FilterCard("sorceries");
                        } else if (cardType.equals(CardType.TRIBAL)){
                            filterCard = new FilterCard("tribal");
                        } else {
                            filterCard = new FilterCard(cardType.toString()+"s");
                        }
                        filterCard.add(new CardTypePredicate(cardType));
                        sourceObject.addAbility(new ProtectionAbility(filterCard));
                    }
                }
            }
        }

//        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
//            Player player = game.getPlayer(playerId);
//            if (player != null) {
//                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), sourceObject.getZoneChangeCounter(game));
//                if (exileId != null) {
//                    for (UUID cardId : game.getState().getExile().getExileZone(exileId)) {
//                        Card card = game.getCard(cardId);
//                        if (card != null) {
//                            for (CardType cardType : card.getCardType()){
//                                FilterCard filterCard = new FilterCard();
//                                filterCard.add(new CardTypePredicate(cardType));
//                                sourceObject.addAbility(new ProtectionAbility(filterCard));
//                            }
//                        }
//                    }
//                }
//            }
//        }

        return true;
    }

    @Override
    public MirrorGolemEffect copy() {
        return new MirrorGolemEffect(this);
    }
}