package mage.cards.a;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ArmedWithProof extends CardImpl {

    public ArmedWithProof(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Armed with Proof enters the battlefield, investigate twice.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect(2)));

        // Clues you control are Equipment in addition to their other types and have "Equipped creature gets +2/+0" and equip {2}.
        this.addAbility(new SimpleStaticAbility(new ArmedWithProofEffect()));
    }

    private ArmedWithProof(final ArmedWithProof card) {
        super(card);
    }

    @Override
    public ArmedWithProof copy() {
        return new ArmedWithProof(this);
    }
}

class ArmedWithProofEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.CLUE);

    ArmedWithProofEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Clues you control are Equipment in addition to their other " +
                "types and have \"Equipped creature gets +2/+0\" and equip {2}.";
    }

    private ArmedWithProofEffect(final ArmedWithProofEffect effect) {
        super(effect);
    }

    @Override
    public ArmedWithProofEffect copy() {
        return new ArmedWithProofEffect(this);
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
