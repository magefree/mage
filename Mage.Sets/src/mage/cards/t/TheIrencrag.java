package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheIrencrag extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public TheIrencrag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Whenever a legendary creature enters the battlefield under your control, you may have The Irencrag become a legendary Equipment artifact named Everflame, Heroes' Legacy. If you do, it gains equip {3} and "Equipped creature gets +3/+3" and loses all other abilities.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new AddContinuousEffectToGame(new TheIrencragBecomesContinuousEffect()),
                filter, true, SetTargetPointer.NONE
        ));
    }

    private TheIrencrag(final TheIrencrag card) {
        super(card);
    }

    @Override
    public TheIrencrag copy() {
        return new TheIrencrag(this);
    }
}

class TheIrencragBecomesContinuousEffect extends ContinuousEffectImpl {

    public TheIrencragBecomesContinuousEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "{this} become a legendary Equipment artifact named Everflame, Heroes' Legacy. "
                + "If you do, it gains equip {3} and \"Equipped creature gets +3/+3\" and loses all other abilities";
        dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
    }

    protected TheIrencragBecomesContinuousEffect(final TheIrencragBecomesContinuousEffect effect) {
        super(effect);
    }

    @Override
    public TheIrencragBecomesContinuousEffect copy() {
        return new TheIrencragBecomesContinuousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent == null) {
            this.discard();
            return false;
        }
        switch (layer) {
            case TextChangingEffects_3:
                permanent.setName("Everflame, Heroes' Legacy");
                return true;
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.addSuperType(game, SuperType.LEGENDARY);
                permanent.addCardType(game, CardType.ARTIFACT);
                permanent.retainAllArtifactSubTypes(game);
                permanent.addSubType(game, SubType.EQUIPMENT);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllAbilities(source.getSourceId(), game);
                permanent.addAbility(
                        new EquipAbility(3, false),
                        source.getSourceId(), game
                );
                permanent.addAbility(
                        new SimpleStaticAbility(new BoostEquippedEffect(3, 3)),
                        source.getSourceId(), game
                );
                return true;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TextChangingEffects_3
                || layer == Layer.TypeChangingEffects_4
                || layer == Layer.AbilityAddingRemovingEffects_6;
    }

}
