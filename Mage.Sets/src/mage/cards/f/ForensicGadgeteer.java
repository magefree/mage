package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ForensicGadgeteer extends CardImpl {

    public ForensicGadgeteer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.VEDALKEN, SubType.ARTIFICER, SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast an artifact spell, investigate.
        this.addAbility(new SpellCastControllerTriggeredAbility(new InvestigateEffect(), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false));

        // Activated abilities of artifacts you control cost {1} less to activate. This effect can't reduce the mana in that cost to less than one mana.
        this.addAbility(new SimpleStaticAbility(new ForensicGadgeteerEffect()));
    }

    private ForensicGadgeteer(final ForensicGadgeteer card) {
        super(card);
    }

    @Override
    public ForensicGadgeteer copy() {
        return new ForensicGadgeteer(this);
    }
}

class ForensicGadgeteerEffect extends CostModificationEffectImpl {

    private static final String effectText = "Activated abilities of artifacts you control cost {1} less to activate." +
            " This effect can't reduce the mana in that cost to less than one mana";

    public ForensicGadgeteerEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    private ForensicGadgeteerEffect(final ForensicGadgeteerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller != null) {
            int reduceMax = CardUtil.calculateActualPossibleGenericManaReduction(abilityToModify.getManaCostsToPay().getMana(), 1, 1);
            if (reduceMax <= 0) {
                return true;
            }
            CardUtil.reduceCost(abilityToModify, reduceMax);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.isActivatedAbility()){
            // Activated abilities of artifacts
            Permanent permanent = game.getPermanentOrLKIBattlefield(abilityToModify.getSourceId());
            return permanent != null
                    && permanent.isArtifact(game)
                    && permanent.isControlledBy(source.getControllerId());
        }
        return false;
    }

    @Override
    public ForensicGadgeteerEffect copy() {
        return new ForensicGadgeteerEffect(this);
    }
}
