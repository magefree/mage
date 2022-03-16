/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.e;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author jeffwadsworth
 */
public final class EaterOfVirtue extends CardImpl {

    public EaterOfVirtue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature dies, exile it.
        this.addAbility(new DiesAttachedTriggeredAbility(new EaterOfVirtueExileEffect(), "equipped creature", false, true, SetTargetPointer.CARD));

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0, Duration.WhileOnBattlefield)));

        // As long as a card exiled with Eater of Virtue has flying, equipped creature has flying. The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, protection, reach, trample, and vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EaterOfVirtueGainAbilityAttachedEffect()));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));

    }

    private EaterOfVirtue(final EaterOfVirtue card) {
        super(card);
    }

    @Override
    public EaterOfVirtue copy() {
        return new EaterOfVirtue(this);
    }
}

class EaterOfVirtueExileEffect extends OneShotEffect {

    EaterOfVirtueExileEffect() {
        super(Outcome.Neutral);
        this.staticText = "exile it";
    }

    EaterOfVirtueExileEffect(final EaterOfVirtueExileEffect effect) {
        super(effect);
    }

    @Override
    public EaterOfVirtueExileEffect copy() {
        return new EaterOfVirtueExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent eaterOfVirtue = game.getPermanent(source.getSourceId());
        Card exiledCard = game.getCard(targetPointer.getFirst(game, source));
        if (controller != null
                && eaterOfVirtue != null
                && exiledCard != null) {
            UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString() + "cards exiled by Eater of Virtue", game);
            controller.moveCardsToExile(exiledCard, source, game, true, exileId, eaterOfVirtue.getIdName());
            return true;
        }
        return false;
    }
}

class EaterOfVirtueGainAbilityAttachedEffect extends ContinuousEffectImpl {

    public EaterOfVirtueGainAbilityAttachedEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "As long as a card exiled with Eater of Virtue has flying, equipped creature has flying. The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, protection, reach, trample, and vigilance";
    }

    public EaterOfVirtueGainAbilityAttachedEffect(final EaterOfVirtueGainAbilityAttachedEffect effect) {
        super(effect);
    }

    @Override
    public EaterOfVirtueGainAbilityAttachedEffect copy() {
        return new EaterOfVirtueGainAbilityAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent eaterOfVirtue = game.getPermanent(source.getSourceId());
        if (eaterOfVirtue != null
                && eaterOfVirtue.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(eaterOfVirtue.getAttachedTo());
            if (permanent != null) {
                UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString() + "cards exiled by Eater of Virtue", game);
                if (game.getState().getExile().getExileZone(exileId) != null
                        && game.getState().getExile().getExileZone(exileId).size() > 0) {
                    Set<Card> cardsInExile = game.getState().getExile().getExileZone(exileId).getCards(game);
                    for (Card card : cardsInExile) {
                        for (Ability a : card.getAbilities()) {
                            if (a instanceof FlyingAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof FirstStrikeAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof DoubleStrikeAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof DeathtouchAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof HasteAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof HexproofAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof IndestructibleAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof LifelinkAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof MenaceAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof ProtectionAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof IndestructibleAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof ReachAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof TrampleAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                            if (a instanceof VigilanceAbility) {
                                permanent.addAbility(a, source.getSourceId(), game);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
