package mage.cards.l;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class LivingLore extends CardImpl {

    public LivingLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Living Lore enters the battlefield, exile an instant or sorcery card from your graveyard.
        this.addAbility(new AsEntersBattlefieldAbility(new LivingLoreExileEffect(),
                "exile an instant or sorcery card from your graveyard"));

        // Living Lore's power and toughness are each equal to the exiled card's converted mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new LivingLoreSetPowerToughnessSourceEffect()));

        // Whenever Living Lore deals combat damage, you may sacrifice it. If you do, 
        // you may cast the exiled card without paying its mana cost.
        this.addAbility(new DealsCombatDamageTriggeredAbility(new LivingLoreSacrificeEffect(), true));
    }

    public LivingLore(final LivingLore card) {
        super(card);
    }

    @Override
    public LivingLore copy() {
        return new LivingLore(this);
    }
}

class LivingLoreExileEffect extends OneShotEffect {

    public LivingLoreExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile an instant or sorcery card from your graveyard";
    }

    public LivingLoreExileEffect(final LivingLoreExileEffect effect) {
        super(effect);
    }

    @Override
    public LivingLoreExileEffect copy() {
        return new LivingLoreExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentEntering(source.getSourceId());
        if (sourcePermanent != null && controller != null) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(
                    new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard"));
            if (controller.chooseTarget(outcome, target, source, game)) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(),
                        game.getState().getZoneChangeCounter(source.getSourceId()) + 1);
                Card card = controller.getGraveyard().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCardsToExile(card, source, game, true, exileId,
                            sourcePermanent.getIdName());
                }
            }
            return true;
        }
        return false;
    }

}

class LivingLoreSetPowerToughnessSourceEffect extends ContinuousEffectImpl {

    public LivingLoreSetPowerToughnessSourceEffect() {
        super(Duration.Custom, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "{this}'s power and toughness are each equal to the exiled card's converted mana cost";
    }

    public LivingLoreSetPowerToughnessSourceEffect(final LivingLoreSetPowerToughnessSourceEffect effect) {
        super(effect);
    }

    @Override
    public LivingLoreSetPowerToughnessSourceEffect copy() {
        return new LivingLoreSetPowerToughnessSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        int zcc = game.getState().getZoneChangeCounter(source.getSourceId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(source.getSourceId());
            zcc++;
        }
        if (permanent == null) {
            return true;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), zcc);
        if (exileId != null) {
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone == null) {
                return false;
            }
            Card exiledCard = null;
            for (Card card : exileZone.getCards(game)) {
                exiledCard = card;
                break;
            }
            if (exiledCard != null) {
                int value = exiledCard.getConvertedManaCost();
                permanent.getPower().setValue(value);
                permanent.getToughness().setValue(value);
            }
        }
        return true;
    }
}

class LivingLoreSacrificeEffect extends OneShotEffect {

    public LivingLoreSacrificeEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may sacrifice it. If you do, you may cast "
                + "the exiled card without paying its mana cost";
    }

    public LivingLoreSacrificeEffect(final LivingLoreSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public LivingLoreSacrificeEffect copy() {
        return new LivingLoreSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject mageObject = source.getSourceObject(game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null
                    && mageObject != null
                    && new MageObjectReference(permanent, game).refersTo(mageObject, game)) {
                if (permanent.sacrifice(source.getSourceId(), game)) {
                    UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(),
                            source.getSourceObjectZoneChangeCounter());
                    if (exileId != null) {
                        ExileZone exileZone = game.getExile().getExileZone(exileId);
                        Card exiledCard = null;
                        if (exileZone != null) {
                            for (Card card : exileZone.getCards(game)) {
                                exiledCard = card;
                                break;
                            }
                        }
                        if (exiledCard != null) {
                            if (exiledCard.getSpellAbility().canChooseTarget(game)) {
                                game.getState().setValue("PlayFromNotOwnHandZone" + exiledCard.getId(), Boolean.TRUE);
                                controller.cast(controller.chooseAbilityForCast(exiledCard, game, true),
                                        game, true, new ApprovingObject(source, game));
                                game.getState().setValue("PlayFromNotOwnHandZone" + exiledCard.getId(), null);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
