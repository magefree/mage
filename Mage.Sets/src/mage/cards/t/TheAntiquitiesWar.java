package mage.cards.t;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class TheAntiquitiesWar extends CardImpl {

    public TheAntiquitiesWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Look at the top five cards of your library. You may reveal an artifact card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new LookLibraryAndPickControllerEffect(5, 1, StaticFilters.FILTER_CARD_ARTIFACT_AN, PutCards.HAND, PutCards.BOTTOM_RANDOM));

        // III — Artifacts you control become artifact creatures with base power and toughness 5/5 until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheAntiquitiesWarEffect());

        this.addAbility(sagaAbility);

    }

    private TheAntiquitiesWar(final TheAntiquitiesWar card) {
        super(card);
    }

    @Override
    public TheAntiquitiesWar copy() {
        return new TheAntiquitiesWar(this);
    }
}

class TheAntiquitiesWarEffect extends ContinuousEffectImpl {

    public TheAntiquitiesWarEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        this.staticText = "Artifacts you control become artifact creatures with base power and toughness 5/5 until end of turn";
    }

    public TheAntiquitiesWarEffect(final TheAntiquitiesWarEffect effect) {
        super(effect);
    }

    @Override
    public TheAntiquitiesWarEffect copy() {
        return new TheAntiquitiesWarEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (Permanent perm : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, source.getControllerId(), source, game)) {
            affectedObjectList.add(new MageObjectReference(perm, game));
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (MageObjectReference mor : affectedObjectList) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            if (!permanent.isArtifact(game)) {
                                permanent.addCardType(game, CardType.ARTIFACT);
                            }
                            if (!permanent.isCreature(game)) {
                                permanent.addCardType(game, CardType.CREATURE);
                            }
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setModifiedBaseValue(5);
                            permanent.getToughness().setModifiedBaseValue(5);
                        }
                }
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
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.PTChangingEffects_7;
    }
}
