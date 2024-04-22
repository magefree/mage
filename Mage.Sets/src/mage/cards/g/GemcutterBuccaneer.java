package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;

/**
 * Gemcutter Buccaneer {3}{R}
 * Creature - Orc Pirate Artificer 1/3
 * Whenever Gemcutter Buccaneer or another Pirate enters the battlefield under your control, create a tapped Treasure token.
 * Treasures you control are Equipment in addition to their other types and have “Equipped creature gets +2/+0,” equip Pirate {1}, and equip {3}.
 *
 * @author DominionSpy
 */
public final class GemcutterBuccaneer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.PIRATE, "Pirate");

    public GemcutterBuccaneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Gemcutter Buccaneer or another Pirate enters the battlefield under your control, create a tapped Treasure token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 1, true),
                filter, false, true));

        // Treasures you control are Equipment in addition to their other types and have "Equipped creature gets +2/+0," equip Pirate {1}, and equip {3}.
        this.addAbility(new SimpleStaticAbility(new GemcutterBuccaneerEffect()));
    }

    private GemcutterBuccaneer(final GemcutterBuccaneer card) {
        super(card);
    }

    @Override
    public GemcutterBuccaneer copy() {
        return new GemcutterBuccaneer(this);
    }
}

/**
 * Based on {@code mage.cards.a.ArterialAlchemyEffect}.
 */
class GemcutterBuccaneerEffect extends ContinuousEffectImpl {

    private static final FilterPermanent treasureFilter = new FilterControlledPermanent(SubType.TREASURE);
    private static final FilterPermanent pirateFilter = new FilterControlledCreaturePermanent(SubType.PIRATE, "Pirate");

    GemcutterBuccaneerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Treasures you control are Equipment in addition to their other types " +
                "and have \"Equipped creature gets +2/+0,\" equip Pirate {1}, and equip {3}.";
    }

    private GemcutterBuccaneerEffect(final GemcutterBuccaneerEffect effect) {
        super(effect);
    }

    @Override
    public GemcutterBuccaneerEffect copy() {
        return new GemcutterBuccaneerEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                treasureFilter, source.getControllerId(), source, game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.EQUIPMENT);
                    break;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(new SimpleStaticAbility(
                            new BoostEquippedEffect(2, 0)
                    ), source.getSourceId(), game);
                    permanent.addAbility(new EquipAbility(
                            Outcome.BoostCreature, new GenericManaCost(1), new TargetPermanent(pirateFilter), false
                    ), source.getSourceId(), game);
                    permanent.addAbility(new EquipAbility(3), source.getSourceId(), game);
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
