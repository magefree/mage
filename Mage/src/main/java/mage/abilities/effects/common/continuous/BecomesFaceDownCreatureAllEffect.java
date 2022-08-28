package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;

/**
 * @author LevelX2
 */

public class BecomesFaceDownCreatureAllEffect extends ContinuousEffectImpl implements SourceEffect {

    protected Map<UUID, Ability> turnFaceUpAbilityMap = new HashMap<>();
    protected FilterPermanent filter;

    public BecomesFaceDownCreatureAllEffect(FilterPermanent filter) {
        super(Duration.EndOfGame, Outcome.BecomeCreature);
        this.filter = filter;
        staticText = "turn all " + filter.getMessage() + " face down. (They're 2/2 creatures.)";
    }

    public BecomesFaceDownCreatureAllEffect(final BecomesFaceDownCreatureAllEffect effect) {
        super(effect);
        for (Map.Entry<UUID, Ability> entry : effect.turnFaceUpAbilityMap.entrySet()) {
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
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (!perm.isFaceDown(game) && !perm.isTransformable()) {
                affectedObjectList.add(new MageObjectReference(perm, game));
                perm.setFaceDown(true, game);
                // check for Morph
                Card card = game.getCard(perm.getId());
                if (card != null) {
                    for (Ability ability : card.getAbilities(game)) {
                        if (ability instanceof MorphAbility) {
                            this.turnFaceUpAbilityMap.put(card.getId(), new TurnFaceUpAbility(((MorphAbility) ability).getMorphCosts()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean targetExists = false;
        for (MageObjectReference mor : affectedObjectList) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null && permanent.isFaceDown(game)) {
                targetExists = true;
                switch (layer) {
                    case TypeChangingEffects_4:
                        permanent.setName("");
                        permanent.getSuperType().clear();
                        permanent.removeAllCardTypes(game);
                        permanent.addCardType(game, CardType.CREATURE);
                        permanent.removeAllSubTypes(game);
                        permanent.getManaCost().clear();
                        break;
                    case ColorChangingEffects_5:
                        permanent.getColor(game).setColor(new ObjectColor());
                        break;
                    case AbilityAddingRemovingEffects_6:
                        Card card = game.getCard(permanent.getId()); //
                        List<Ability> abilitiesToRemove = new ArrayList<>();
                        for (Ability ability : permanent.getAbilities()) {

                            // keep gained abilities from other sources, removes only own (card text)
                            if (card != null && !card.getAbilities().contains(ability)) {
                                continue;
                            }

                            // 701.33c
                            // If a card with morph is manifested, its controller may turn that card face up using
                            // either the procedure described in rule 702.36e to turn a face-down permanent with morph face up
                            // or the procedure described above to turn a manifested permanent face up.
                            //
                            // so keep all tune face up abilities and other face down compatible
                            if (ability.getWorksFaceDown()) {
                                ability.setRuleVisible(false);
                                continue;
                            }

                            if (!ability.getRuleVisible() && !ability.getEffects().isEmpty()) {
                                if (ability.getEffects().get(0) instanceof BecomesFaceDownCreatureAllEffect) {
                                    continue;
                                }
                            }
                            abilitiesToRemove.add(ability);
                        }
                        permanent.removeAbilities(abilitiesToRemove, source.getSourceId(), game);
                        if (turnFaceUpAbilityMap.containsKey(permanent.getId())) {
                            permanent.addAbility(turnFaceUpAbilityMap.get(permanent.getId()), source.getSourceId(), game);
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setModifiedBaseValue(2);
                            permanent.getToughness().setModifiedBaseValue(2);
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
