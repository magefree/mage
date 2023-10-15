package mage.cards.d;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DanLewis extends CardImpl {

    public DanLewis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Noncreature, non-Equipment artifacts you control are Equipment in addition to their other types and have "Equipped creature gets +1/+0" and equip {1}.
        this.addAbility(new SimpleStaticAbility(new DanLewisEffect()));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private DanLewis(final DanLewis card) {
        super(card);
    }

    @Override
    public DanLewis copy() {
        return new DanLewis(this);
    }
}

class DanLewisEffect extends ContinuousEffectImpl {
    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(SubType.EQUIPMENT.getPredicate()));
    }

    public DanLewisEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "noncreature, non-Equipment artifacts you control are Equipment " +
                "in addition to their other types and have \"Equipped creature gets +1/+0\" and equip {1}";
    }

    private DanLewisEffect(final DanLewisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (layer == Layer.TypeChangingEffects_4) {
            affectedObjectList.clear();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                affectedObjectList.add(new MageObjectReference(permanent, game));
            }
        }
        for (MageObjectReference mor : affectedObjectList) {
            Permanent permanent = mor.getPermanent(game);
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.EQUIPMENT);
                    break;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(new SimpleStaticAbility(
                            new BoostEquippedEffect(1, 0)
                    ), source.getSourceId(), game);
                    permanent.addAbility(new EquipAbility(1, false), source.getSourceId(), game);
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public DanLewisEffect copy() {
        return new DanLewisEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
                return true;
            default:
                return false;
        }
    }
}
