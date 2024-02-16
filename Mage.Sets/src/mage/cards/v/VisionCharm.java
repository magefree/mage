package mage.cards.v;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceBasicLandType;
import mage.choices.ChoiceLandType;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;

import java.util.Iterator;
import java.util.UUID;

/**
 * @author tre3qwerty
 */
public final class VisionCharm extends CardImpl {

    public VisionCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Choose one - Target player puts the top four cards of their library into their graveyard;
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // or choose a land type and a basic land type. Each land of the first chosen type becomes the second chosen type until end of turn;
        this.getSpellAbility().addMode(new Mode(new VisionCharmEffect()));

        // or target artifact phases out.
        Mode mode = new Mode(new PhaseOutTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private VisionCharm(final VisionCharm card) {
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

    VisionCharmEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "Choose a land type and a basic land type. Each land of the first chosen type becomes the second chosen type until end of turn.";
    }

    private VisionCharmEffect(final VisionCharmEffect effect) {
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
        filter.add(SubType.byDescription(targetLandType).getPredicate());
        if (this.affectedObjectsSet) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                affectedObjectList.add(new MageObjectReference(permanent, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent land = it.next().getPermanent(game);
            if (land == null) {
                it.remove();
                continue;
            }
            land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
            land.addSubType(game, targetBasicLandType);
            land.removeAllAbilities(source.getSourceId(), game);
            switch (targetBasicLandType) {
                case FOREST:
                    land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                    break;
                case PLAINS:
                    land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                    break;
                case MOUNTAIN:
                    land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                    break;
                case ISLAND:
                    land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                    break;
                case SWAMP:
                    land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                    break;
            }
        }

        return true;
    }
}
