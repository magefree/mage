package mage.cards.d;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsBecomesAttachedToCreatureSourceAbility;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

/**
 * Dinosaur Headdress
 * Artifact - Equipment
 * When Dinosaur Headdress enters the battlefield, attach it to target creature you control.
 * As Dinosaur Headdress becomes attached to a creature, choose an exiled creature card used to craft Dinosaur Headdress.
 * Equipped creature is a copy of the last chosen card.
 * Equip {2}
 *
 * @author DominionSpy
 */
public class DinosaurHeaddress extends CardImpl {

    public DinosaurHeaddress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.subtype.add(SubType.EQUIPMENT);

        // When Dinosaur Headdress enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // As Dinosaur Headdress becomes attached to a creature, choose an exiled creature card used to craft Dinosaur Headdress.
        this.addAbility(new AsBecomesAttachedToCreatureSourceAbility(new ChooseAnExiledCreatureCard()));

        // Equipped creature is a copy of the last chosen card.
        this.addAbility(new SimpleStaticAbility(new DinosaurHeaddressEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    protected DinosaurHeaddress(final DinosaurHeaddress card) {
        super(card);
    }

    @Override
    public DinosaurHeaddress copy() {
        return new DinosaurHeaddress(this);
    }
}

class ChooseAnExiledCreatureCard extends OneShotEffect {

    public static final String INFO_KEY = "CHOSEN_CREATURE_CARD";

    public ChooseAnExiledCreatureCard() {
        super(Outcome.Copy);
        staticText = "choose an exiled creature card used to craft {this}.";
    }

    protected ChooseAnExiledCreatureCard(final ChooseAnExiledCreatureCard effect) {
        super(effect);
    }

    @Override
    public ChooseAnExiledCreatureCard copy() {
        return new ChooseAnExiledCreatureCard(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null) {
            return false;
        }

        Target target = new TargetCardInExile(new FilterCreatureCard(),
                CardUtil.getExileZoneId(game, source.getSourceId(),
                        game.getState().getZoneChangeCounter(source.getSourceId()) - 2
                ));
        target.withNotTarget(true);
        if (!target.canChoose(controller.getId(), source, game)) {
            return true;
        }
        controller.choose(Outcome.Copy, target, source, game);
        Card chosenCreatureCard = game.getCard(target.getFirstTarget());
        if (chosenCreatureCard != null) {
            game.getState().setValue(source.getSourceId().toString() + INFO_KEY, chosenCreatureCard.copy());
        }
        return true;
    }
}

class DinosaurHeaddressEffect extends ContinuousEffectImpl {

    DinosaurHeaddressEffect() {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.Copy);
        this.staticText = "Equipped creature is a copy of the last chosen card.";
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
        Permanent equipment = game.getPermanent(source.getSourceId());
        MageObject copied = (MageObject) game.getState()
                .getValue(source.getSourceId().toString() + ChooseAnExiledCreatureCard.INFO_KEY);
        if (equipment == null || copied == null) {
            return false;
        }

        Permanent permanent = game.getPermanent(equipment.getAttachedTo());
        if (permanent == null) {
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
