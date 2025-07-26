package mage.cards.a;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BloodToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArterialAlchemy extends CardImpl {

    public ArterialAlchemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // When Arterial Alchemy enters the battlefield, create a Blood token for each opponent you have.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new BloodToken(), OpponentsCount.instance)
                        .setText("create a Blood token for each opponent you have")
        ));

        // Blood tokens you control are Equipment in addition to their other types and have "Equipped creature gets +2/+0" and equip {2}.
        this.addAbility(new SimpleStaticAbility(new ArterialAlchemyEffect()));
    }

    private ArterialAlchemy(final ArterialAlchemy card) {
        super(card);
    }

    @Override
    public ArterialAlchemy copy() {
        return new ArterialAlchemy(this);
    }
}

class ArterialAlchemyEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BLOOD);

    static {
        filter.add(TokenPredicate.TRUE);
    }

    ArterialAlchemyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Blood tokens you control are Equipment in addition " +
                "to their other types and have \"Equipped creature gets +2/+0\" and equip {2}.";
    }

    private ArterialAlchemyEffect(final ArterialAlchemyEffect effect) {
        super(effect);
    }

    @Override
    public ArterialAlchemyEffect copy() {
        return new ArterialAlchemyEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.EQUIPMENT);
                    break;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(new SimpleStaticAbility(
                            new BoostEquippedEffect(2, 0)
                    ), source.getSourceId(), game);
                    permanent.addAbility(new EquipAbility(2), source.getSourceId(), game);
                    break;
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        affectedObjects.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game));
        return !affectedObjects.isEmpty();
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        List<MageItem> affectedObjects = new ArrayList<>();
        if (queryAffectedObjects(layer, source, game, affectedObjects)) {
            applyToObjects(layer, sublayer, source, game, affectedObjects);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
