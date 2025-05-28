package mage.cards.m;

import mage.MageObject;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.StaticHint;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPoolItem;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author duncant
 */
public final class MycosynthLattice extends CardImpl {

    public MycosynthLattice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // All permanents are artifacts in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new PermanentsAreArtifactsEffect()));

        // All cards that aren't on the battlefield, spells, and permanents are colorless.
        this.addAbility(new SimpleStaticAbility(new EverythingIsColorlessEffect()));

        // Players may spend mana as though it were mana of any color.
        Ability ability = new SimpleStaticAbility(new ManaCanBeSpentAsAnyColorEffect());
        ability.addHint(new StaticHint("(XMage hint: You can use floating mana by clicking on the related symbol of the needed mana type in your mana pool player area.)"));
        this.addAbility(ability);
    }

    private MycosynthLattice(final MycosynthLattice card) {
        super(card);
    }

    @Override
    public MycosynthLattice copy() {
        return new MycosynthLattice(this);
    }
}

class PermanentsAreArtifactsEffect extends ContinuousEffectImpl {

    PermanentsAreArtifactsEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "All permanents are artifacts in addition to their other types";
        this.effectCardZones.add(Zone.BATTLEFIELD);
        this.dependencyTypes.add(DependencyType.ArtifactAddingRemoving); // March of the Machines
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            perm.addCardType(game, CardType.ARTIFACT);
        }
        return true;
    }

    @Override
    public PermanentsAreArtifactsEffect copy() {
        return new PermanentsAreArtifactsEffect(this);
    }

    private PermanentsAreArtifactsEffect(PermanentsAreArtifactsEffect effect) {
        super(effect);
    }
}

class EverythingIsColorlessEffect extends ContinuousEffectImpl {

    EverythingIsColorlessEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Neutral);
        staticText = "All cards that aren't on the battlefield, spells, and permanents are colorless";
        this.effectCardZones.add(Zone.ALL);
        this.playersToCheck = TargetController.EACH_PLAYER;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (affectedObjectList.isEmpty()) {
            return false;
        }
        ObjectColor colorless = new ObjectColor();

        for (MageObjectReference mor : affectedObjectList) {
            if (mor.getPermanent(game) != null) {
                mor.getPermanent(game).getColor(game).setColor(colorless);
            } else {
                MageObject affectedObject = mor.getSpell(game);
                if (affectedObject == null) {
                    affectedObject = mor.getCard(game);
                }
                if (affectedObject == null) {
                    continue;
                }
                game.getState().getCreateMageObjectAttribute(affectedObject, game)
                        .getColor()
                        .setColor(ObjectColor.COLORLESS);
            }
        }
        return true;
    }

    @Override
    public EverythingIsColorlessEffect copy() {
        return new EverythingIsColorlessEffect(this);
    }

    private EverythingIsColorlessEffect(EverythingIsColorlessEffect effect) {
        super(effect);
    }
}

class ManaCanBeSpentAsAnyColorEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public ManaCanBeSpentAsAnyColorEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Players may spend mana as though it were mana of any color";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return game.getState().getPlayersInRange(source.getControllerId(), game).contains(affectedControllerId);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }

    @Override
    public ManaCanBeSpentAsAnyColorEffect copy() {
        return new ManaCanBeSpentAsAnyColorEffect(this);
    }

    private ManaCanBeSpentAsAnyColorEffect(ManaCanBeSpentAsAnyColorEffect effect) {
        super(effect);
    }
}
