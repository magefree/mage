package mage.cards.i;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceBasicLandType;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class IllusionaryTerrain extends CardImpl {

    public IllusionaryTerrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        // Cumulative upkeep {2}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{2}")));

        // As Illusionary Terrain enters the battlefield, choose two basic land types.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseTwoBasicLandTypesEffect(Outcome.Neutral)));

        // Basic lands of the first chosen type are the second chosen type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IllusionaryTerrainEffect()));

    }

    private IllusionaryTerrain(final IllusionaryTerrain card) {
        super(card);
    }

    @Override
    public IllusionaryTerrain copy() {
        return new IllusionaryTerrain(this);
    }
}

class IllusionaryTerrainEffect extends ContinuousEffectImpl {

    public IllusionaryTerrainEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Basic lands of the first chosen type are the second chosen type";
    }

    public IllusionaryTerrainEffect(final IllusionaryTerrainEffect effect) {
        super(effect);
    }

    @Override
    public IllusionaryTerrainEffect copy() {
        return new IllusionaryTerrainEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        SubType firstChoice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + "firstChoice"));
        SubType secondChoice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + "secondChoice"));
        List<Permanent> lands = game.getBattlefield().getAllActivePermanents(CardType.LAND);
        if (controller != null
                && firstChoice != null
                && secondChoice != null) {
            for (Permanent land : lands) {
                if (land != null
                        && land.isBasic()) {
                    switch (layer) {
                        case TypeChangingEffects_4:
                            if (sublayer == SubLayer.NA) {
                                land.getSubtype(game).clear();
                                land.getSubtype(game).add(secondChoice);
                            }
                            break;
                        case AbilityAddingRemovingEffects_6:
                            if (sublayer == SubLayer.NA) {
                                boolean addAbility = true;
                                land.getAbilities().clear();
                                if (secondChoice.equals(SubType.FOREST)) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof GreenManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (secondChoice.equals(SubType.PLAINS)) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof WhiteManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (secondChoice.equals(SubType.MOUNTAIN)) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof RedManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (secondChoice.equals(SubType.ISLAND)) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof BlueManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (secondChoice.equals(SubType.SWAMP)) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof BlackManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                                    }
                                }
                            }
                            break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.TypeChangingEffects_4;
    }
}

class ChooseTwoBasicLandTypesEffect extends OneShotEffect {

    String choiceOne;
    String choiceTwo;

    public ChooseTwoBasicLandTypesEffect(Outcome outcome) {
        super(outcome);
        this.staticText = "choose two basic land types";
    }

    public ChooseTwoBasicLandTypesEffect(final ChooseTwoBasicLandTypesEffect effect) {
        super(effect);
    }

    @Override
    public ChooseTwoBasicLandTypesEffect copy() {
        return new ChooseTwoBasicLandTypesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source.getSourceId());
        }
        if (controller != null
                && mageObject != null) {
            ChoiceImpl choices = new ChoiceBasicLandType();
            if (controller.choose(Outcome.Neutral, choices, game)) {
                game.informPlayers(mageObject.getName() + ":  First chosen basic land type is " + choices.getChoice());
                game.getState().setValue(mageObject.getId().toString() + "firstChoice", choices.getChoice());
                choiceOne = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + "firstChoice")).getDescription();
            }
            if (controller.choose(Outcome.Neutral, choices, game)) {
                game.informPlayers(mageObject.getName() + ":  Second chosen basic land type is " + choices.getChoice());
                game.getState().setValue(mageObject.getId().toString() + "secondChoice", choices.getChoice());
                choiceTwo = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + "secondChoice")).getDescription();
                if (mageObject instanceof Permanent
                        && choiceOne != null
                        && choiceTwo != null) {
                    ((Permanent) mageObject).addInfo("Chosen Types", CardUtil.addToolTipMarkTags("First chosen basic land type: " + choiceOne
                            + "\n Second chosen basic land type: " + choiceTwo), game);
                }
                return true;
            }
        }
        return false;
    }
}
