package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class DancingSword extends CardImpl {

    public DancingSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // When equipped creature dies, you may have Dancing Sword become a 2/1 Construct artifact creature with flying and ward {1}. If you do, it isn't an Equipment.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new DancingSwordEffect(), "equipped creature", true
        ).setTriggerPhrase("When equipped creature dies, "));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetControlledCreaturePermanent(), false));
    }

    private DancingSword(final DancingSword card) {
        super(card);
    }

    @Override
    public DancingSword copy() {
        return new DancingSword(this);
    }
}

class DancingSwordEffect extends ContinuousEffectImpl {

    DancingSwordEffect() {
        super(Duration.Custom, Outcome.Benefit);
        staticText = "you may have {this} become a 2/1 Construct artifact creature " +
                "with flying and ward {1}. If you do, it isn't an Equipment";
    }

    private DancingSwordEffect(final DancingSwordEffect effect) {
        super(effect);
    }

    @Override
    public DancingSwordEffect copy() {
        return new DancingSwordEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes();
                permanent.addCardType(game, CardType.ARTIFACT);
                permanent.addCardType(game, CardType.CREATURE);
                permanent.removeAllSubTypes(game);
                permanent.addSubType(game, SubType.CONSTRUCT);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                permanent.addAbility(new WardAbility(new GenericManaCost(1)), source.getSourceId(), game);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(2);
                    permanent.getToughness().setModifiedBaseValue(1);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
