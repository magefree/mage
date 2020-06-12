package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.PursuedWhaleToken;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PursuedWhale extends CardImpl {

    public PursuedWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // When Pursued Whale enters the battlefield, each opponent creates a 1/1 red Pirate creature token with "This creature can't block" and "Creatures you control attack each combat if able."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PursuedWhaleTokenEffect()));

        // Spells your opponents cast that target Pursued Whale cost {3} more to cast.
        this.addAbility(new SimpleStaticAbility(new PursuedWhaleCostIncreaseEffect()));
    }

    private PursuedWhale(final PursuedWhale card) {
        super(card);
    }

    @Override
    public PursuedWhale copy() {
        return new PursuedWhale(this);
    }
}

class PursuedWhaleTokenEffect extends OneShotEffect {

    private static final Token token = new PursuedWhaleToken();

    PursuedWhaleTokenEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent creates a 1/1 red Pirate creature token with " +
                "\"This creature can't block\" and \"Creatures you control attack each combat if able.\"";
    }

    private PursuedWhaleTokenEffect(final PursuedWhaleTokenEffect effect) {
        super(effect);
    }

    @Override
    public PursuedWhaleTokenEffect copy() {
        return new PursuedWhaleTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            token.putOntoBattlefield(1, game, source.getSourceId(), playerId);
        }
        return true;
    }
}

class PursuedWhaleCostIncreaseEffect extends CostModificationEffectImpl {

    PursuedWhaleCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target {this} cost {3} more to cast";
    }

    private PursuedWhaleCostIncreaseEffect(PursuedWhaleCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -3);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)
                || !game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }
        return abilityToModify
                .getModes()
                .getSelectedModes()
                .stream()
                .map(uuid -> abilityToModify.getModes().get(uuid))
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .anyMatch(uuid -> uuid.equals(source.getSourceId()));
    }

    @Override
    public PursuedWhaleCostIncreaseEffect copy() {
        return new PursuedWhaleCostIncreaseEffect(this);
    }
}
