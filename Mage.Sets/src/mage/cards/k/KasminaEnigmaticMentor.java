package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
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
import mage.target.Target;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KasminaEnigmaticMentor extends CardImpl {

    public KasminaEnigmaticMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KASMINA);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // Spells your opponents cast that target a creature or planeswalker you control cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new KasminaEnigmaticMentorCostReductionEffect()));

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

class KasminaEnigmaticMentorCostReductionEffect extends CostModificationEffectImpl {

    KasminaEnigmaticMentorCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target a creature or planeswalker you control cost {2} more to cast";
    }

    private KasminaEnigmaticMentorCostReductionEffect(KasminaEnigmaticMentorCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getAbilityType() != AbilityType.SPELL
                || !game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }
        for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
            Mode mode = abilityToModify.getModes().get(modeId);
            for (Target target : mode.getTargets()) {
                for (UUID targetUUID : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetUUID);
                    if (permanent != null
                            && (permanent.isCreature() || permanent.isPlaneswalker())
                            && permanent.isControlledBy(source.getControllerId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public KasminaEnigmaticMentorCostReductionEffect copy() {
        return new KasminaEnigmaticMentorCostReductionEffect(this);
    }

}