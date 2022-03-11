package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author noahg
 */
public final class MirrorGolem extends CardImpl {

    public MirrorGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Imprint - When Mirror Golem enters the battlefield, you may exile target card from a graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MirrorGolemImprintEffect(), true);
        ability.addTarget(new TargetCardInGraveyard());
        ability.setAbilityWord(AbilityWord.IMPRINT);
        this.addAbility(ability);

        // Mirror Golem has protection from each of the exiled card's card types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MirrorGolemEffect()));
    }

    private MirrorGolem(final MirrorGolem card) {
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
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source.getSourceId()));

        if (sourceObject == null || sourceObject.getImprinted() == null) {
            return false;
        }

        for (UUID imprinted : sourceObject.getImprinted()) {
            if (imprinted != null && exileZone.contains(imprinted)) {
                Card card = game.getCard(imprinted);
                if (card != null) {
                    for (CardType cardType : card.getCardType(game)) {
                        FilterCard filterCard;
                        if (cardType.equals(CardType.SORCERY)) {
                            filterCard = new FilterCard("sorceries");
                        } else if (cardType.equals(CardType.TRIBAL)) {
                            filterCard = new FilterCard("tribal");
                        } else {
                            filterCard = new FilterCard(cardType.toString() + "s");
                        }
                        filterCard.add(cardType.getPredicate());
                        sourceObject.addAbility(new ProtectionAbility(filterCard), source.getSourceId(), game);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public MirrorGolemEffect copy() {
        return new MirrorGolemEffect(this);
    }
}
