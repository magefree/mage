package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WizardToken;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KasminaEnigmaticMentor extends CardImpl {

    public KasminaEnigmaticMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KASMINA);
        this.setStartingLoyalty(5);

        // Spells your opponents cast that target a creature or planeswalker you control cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new KasminaEnigmaticMentorCostModificationEffect()));

        // -2: Create a 2/2 blue Wizard creature token. Draw a card, then discard a card.
        Ability ability = new LoyaltyAbility(new CreateTokenEffect(new WizardToken()), -2);
        ability.addEffect(new DrawDiscardControllerEffect(
                1, 1
        ).setText("Draw a card, then discard a card."));
        this.addAbility(ability);
    }

    private KasminaEnigmaticMentor(final KasminaEnigmaticMentor card) {
        super(card);
    }

    @Override
    public KasminaEnigmaticMentor copy() {
        return new KasminaEnigmaticMentor(this);
    }
}

class KasminaEnigmaticMentorCostModificationEffect extends CostModificationEffectImpl {

    KasminaEnigmaticMentorCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target a creature or planeswalker you control cost {2} more to cast";
    }

    private KasminaEnigmaticMentorCostModificationEffect(KasminaEnigmaticMentorCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }

        if (!game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }

        Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
        Set<UUID> allTargets;
        if (spell != null) {
            // real cast
            allTargets = CardUtil.getAllSelectedTargets(abilityToModify, game);
        } else {
            // playable
            allTargets = CardUtil.getAllPossibleTargets(abilityToModify, game);

            // can target without cost increase
            if (allTargets.stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .anyMatch(permanent -> !isTargetCompatible(permanent, source, game))) {
                return false;
            }
            ;
        }

        return allTargets.stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> isTargetCompatible(permanent, source, game));
    }

    private boolean isTargetCompatible(Permanent permanent, Ability source, Game game) {
        // target a creature or planeswalker you control
        return permanent.isControlledBy(source.getControllerId())
                && (permanent.isCreature(game) || permanent.isPlaneswalker(game));
    }

    @Override
    public KasminaEnigmaticMentorCostModificationEffect copy() {
        return new KasminaEnigmaticMentorCostModificationEffect(this);
    }
}