package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsBecomesAttachedToCreatureSourceAbility;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author DominionSpy, xenohedron
 */
public final class PaleontologistsPickAxe extends TransformingDoubleFacedCard {

    public PaleontologistsPickAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "{2}",
                "Dinosaur Headdress",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, ""
        );

        // Whenever equipped creature attacks, draw a card, then discard a card.
        this.getLeftHalfCard().addAbility(new AttacksAttachedTriggeredAbility(
                new DrawDiscardControllerEffect()
        ));

        // Equip {1}
        this.getLeftHalfCard().addAbility(new EquipAbility(1, false));

        // Craft with one or more creatures {5}
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{5}", "one or more creatures",
                "other creatures you control and/or creature cards in your graveyard",
                1, Integer.MAX_VALUE, CardType.CREATURE.getPredicate()
        ));

        // Dinosaur Headdress

        // When this Equipment enters, attach it to target creature you control.
        this.getRightHalfCard().addAbility(new EntersBattlefieldAttachToTarget());

        // As this Equipment becomes attached to a creature, choose an exiled creature card used to craft this Equipment.
        this.getRightHalfCard().addAbility(new AsBecomesAttachedToCreatureSourceAbility(new DinosaurHeaddressChooseEffect()));

        // Equipped creature is a copy of the last chosen card.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new DinosaurHeaddressEffect()));

        // Equip {2}
        this.getRightHalfCard().addAbility(new EquipAbility(2, false));
    }

    private PaleontologistsPickAxe(final PaleontologistsPickAxe card) {
        super(card);
    }

    @Override
    public PaleontologistsPickAxe copy() {
        return new PaleontologistsPickAxe(this);
    }
}

class DinosaurHeaddressChooseEffect extends OneShotEffect {

    DinosaurHeaddressChooseEffect() {
        super(Outcome.Copy);
        staticText = "choose an exiled creature card used to craft this Equipment";
    }

    private DinosaurHeaddressChooseEffect(final DinosaurHeaddressChooseEffect effect) {
        super(effect);
    }

    @Override
    public DinosaurHeaddressChooseEffect copy() {
        return new DinosaurHeaddressChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent equipment = source.getSourcePermanentIfItStillExists(game);
        if (controller == null || equipment == null) {
            return false;
        }
        Target target = new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURE,
                CardUtil.getExileZoneId(game, equipment.getMainCard().getId(),
                        game.getState().getZoneChangeCounter(equipment.getMainCard().getId()) - 1
                ));
        target.withNotTarget(true);
        if (!target.canChoose(controller.getId(), source, game)) {
            return true;
        }
        controller.choose(Outcome.Copy, target, source, game);
        Card chosenCreatureCard = game.getCard(target.getFirstTarget());
        if (chosenCreatureCard != null) {
            game.getState().setValue(getInfoKey(equipment, game), chosenCreatureCard.copy());
        }
        return true;
    }

    static String getInfoKey(Permanent equipment, Game game) {
        return "CHOSEN_CREATURE_CARD" + equipment.getId().toString() + equipment.getZoneChangeCounter(game);
    }
}

class DinosaurHeaddressEffect extends ContinuousEffectImpl {

    DinosaurHeaddressEffect() {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.Copy);
        this.staticText = "Equipped creature is a copy of the last chosen card";
    }

    protected DinosaurHeaddressEffect(final DinosaurHeaddressEffect effect) {
        super(effect);
    }

    @Override
    public DinosaurHeaddressEffect copy() {
        return new DinosaurHeaddressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = source.getSourcePermanentIfItStillExists(game);
        Permanent permanent = source.getPermanentSourceAttachedToIfItStillExists(game);
        if (equipment == null || permanent == null) {
            return false;
        }
        MageObject copied = (MageObject) game.getState()
                .getValue(DinosaurHeaddressChooseEffect.getInfoKey(equipment, game));
        if (copied == null) {
            return false;
        }
        permanent.setName(copied.getName());
        permanent.getManaCost().clear();
        permanent.getManaCost().addAll(copied.getManaCost());

        CardUtil.copySetAndCardNumber(permanent, copied);

        permanent.removeAllCardTypes(game);
        for (SuperType t : copied.getSuperType(game)) {
            permanent.addSuperType(game, t);
        }
        permanent.removeAllCardTypes(game);
        for (CardType cardType : copied.getCardType(game)) {
            permanent.addCardType(game, cardType);
        }
        permanent.removeAllSubTypes(game);
        permanent.copySubTypesFrom(game, copied);
        permanent.getColor(game).setColor(copied.getColor(game));
        permanent.removeAllAbilities(source.getSourceId(), game);
        for (Ability ability : copied.getAbilities()) {
            permanent.addAbility(ability, source.getSourceId(), game, true);
        }
        permanent.getPower().setModifiedBaseValue(copied.getPower().getBaseValue());
        permanent.getToughness().setModifiedBaseValue(copied.getToughness().getBaseValue());
        return true;
    }
}
