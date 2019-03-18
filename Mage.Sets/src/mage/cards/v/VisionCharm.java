
package mage.cards.v;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceBasicLandType;
import mage.choices.ChoiceLandType;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author tre3qwerty
 */
public final class VisionCharm extends CardImpl {

    public VisionCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Choose one - Target player puts the top four cards of their library into their graveyard;
        this.getSpellAbility().addEffect(new PutLibraryIntoGraveTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // or choose a land type and a basic land type. Each land of the first chosen type becomes the second chosen type until end of turn;
        Mode mode = new Mode();
        mode.addEffect(new VisionCharmEffect());
        this.getSpellAbility().addMode(mode);

        // or target artifact phases out.
        mode = new Mode();
        mode.addEffect(new PhaseOutTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    public VisionCharm(final VisionCharm card) {
        super(card);
    }

    @Override
    public VisionCharm copy() {
        return new VisionCharm(this);
    }
}

class VisionCharmEffect extends ContinuousEffectImpl {

    private String targetLandType;
    private SubType targetBasicLandType;

    public VisionCharmEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "Choose a land type and a basic land type. Each land of the first chosen type becomes the second chosen type until end of turn.";
    }

    public VisionCharmEffect(final VisionCharmEffect effect) {
        super(effect);
        targetLandType = effect.targetLandType;
        targetBasicLandType = effect.targetBasicLandType;
    }

    @Override
    public VisionCharmEffect copy() {
        return new VisionCharmEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceLandType();
            if (!controller.choose(outcome, choice, game)) {
                discard();
                return;
            }
            targetLandType = choice.getChoice();
            choice = new ChoiceBasicLandType();
            if (!controller.choose(outcome, choice, game)) {
                discard();
                return;
            }
            targetBasicLandType = SubType.byDescription(choice.getChoice());
            if (targetLandType == null || targetBasicLandType == null) {
                this.discard();
                return;
            }
        } else {
            this.discard();
            return;
        }
        FilterPermanent filter = new FilterLandPermanent();
        filter.add(new SubtypePredicate(SubType.byDescription(targetLandType)));
        if (this.affectedObjectsSet) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                affectedObjectList.add(new MageObjectReference(permanent, game));
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        // TODO fix to use SubType enum
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent land = it.next().getPermanent(game);
            if (land != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        land.getSubtype(game).clear();
                        land.getSubtype(game).add(targetBasicLandType);
                        break;
                    case AbilityAddingRemovingEffects_6:
                        if (sublayer == SubLayer.NA) {
                            land.getAbilities().clear();
                            if (targetBasicLandType.equals(SubType.FOREST)) {
                                land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                            }
                            if (targetBasicLandType.equals(SubType.PLAINS)) {
                                land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                            }
                            if (targetBasicLandType.equals(SubType.MOUNTAIN)) {
                                land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                            }
                            if (targetBasicLandType.equals(SubType.ISLAND)) {
                                land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                            }
                            if (targetBasicLandType.equals(SubType.SWAMP)) {
                                land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                            }
                        }
                }
            } else {
                it.remove();
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
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
