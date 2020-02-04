
package mage.abilities.effects.common.continuous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */

public class BecomesFaceDownCreatureAllEffect extends ContinuousEffectImpl implements SourceEffect {

    protected Map<UUID,Ability> turnFaceUpAbilityMap = new HashMap<>();
    protected FilterPermanent filter;

    public BecomesFaceDownCreatureAllEffect(FilterPermanent filter) {
        super(Duration.EndOfGame, Outcome.BecomeCreature);
        this.filter = filter;
        staticText = "turn all " + filter.getMessage() + " face down. (They're 2/2 creatures.)";
    }

    public BecomesFaceDownCreatureAllEffect(final BecomesFaceDownCreatureAllEffect effect) {
        super(effect);
        for (Map.Entry<UUID,Ability> entry: effect.turnFaceUpAbilityMap.entrySet()) {
            this.turnFaceUpAbilityMap.put(entry.getKey(), entry.getValue());
        }
        this.filter = effect.filter.copy();
    }

    @Override
    public BecomesFaceDownCreatureAllEffect copy() {
        return new BecomesFaceDownCreatureAllEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (!perm.isFaceDown(game) && !perm.isTransformable()) {
                affectedObjectList.add(new MageObjectReference(perm, game));
                perm.setFaceDown(true, game);
                // check for Morph
                Card card = game.getCard(perm.getId());
                if (card != null) {
                    for (Ability ability: card.getAbilities()) {
                        if (ability instanceof MorphAbility) {
                            this.turnFaceUpAbilityMap.put(card.getId(), new TurnFaceUpAbility(((MorphAbility)ability).getMorphCosts()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean targetExists = false;
        for (MageObjectReference mor: affectedObjectList) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null && permanent.isFaceDown(game)) {
                targetExists = true;
                switch (layer) {
                    case TypeChangingEffects_4:
                        permanent.setName("");
                        permanent.getSuperType().clear();
                        permanent.getCardType().clear();
                        permanent.addCardType(CardType.CREATURE);
                        permanent.getSubtype(game).clear();
                        permanent.getManaCost().clear();
                        break;
                    case ColorChangingEffects_5:
                        permanent.getColor(game).setColor(new ObjectColor());
                        break;
                    case AbilityAddingRemovingEffects_6:
                        Card card = game.getCard(permanent.getId()); //
                        List<Ability> abilities = new ArrayList<>();
                        for (Ability ability : permanent.getAbilities()) {
                            if (card != null && !card.getAbilities().contains(ability)) {
                                // gained abilities from other sources won't be removed
                                continue;
                            }
                            // TODO: Add flag "works also face down" to ability and use it to control ability removement instead of instanceof check
                            if (ability.getWorksFaceDown()) {
                                ability.setRuleVisible(false);
                                continue;
                            }
                            if (!ability.getRuleVisible() && !ability.getEffects().isEmpty()) {
                                if (ability.getEffects().get(0) instanceof BecomesFaceDownCreatureAllEffect) {
                                    continue;
                                }
                            }
                            abilities.add(ability);
                        }
                        permanent.getAbilities().removeAll(abilities);
                        if (turnFaceUpAbilityMap.containsKey(permanent.getId())) {
                            permanent.addAbility(turnFaceUpAbilityMap.get(permanent.getId()), game);
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setValue(2);
                            permanent.getToughness().setValue(2);
                        }

                }
            }
        }
        if (!targetExists) {
            discard();
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}
