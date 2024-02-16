package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.UUID;

public class HeliodTheWarpedEclipse extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("spells");

    public HeliodTheWarpedEclipse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);
        this.nightCard = true;

        // You may cast spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)));

        // Spells you cast cost {1} less to cast for each card your opponents have drawn this turn.
        this.addAbility(new SimpleStaticAbility(new HeliodTheWarpedEclipseEffect()));
    }

    private HeliodTheWarpedEclipse(final HeliodTheWarpedEclipse card) {
        super(card);
    }

    @Override
    public HeliodTheWarpedEclipse copy() {
        return new HeliodTheWarpedEclipse(this);
    }
}

class HeliodTheWarpedEclipseEffect extends CostModificationEffectImpl {

    HeliodTheWarpedEclipseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "spells you cast cost {1} less to cast for each card your opponents have drawn this turn";
    }

    private HeliodTheWarpedEclipseEffect(final HeliodTheWarpedEclipseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        int amount = game
                .getOpponents(source.getControllerId())
                .stream()
                .mapToInt(watcher::getCardsDrawnThisTurn)
                .sum();
        if (amount < 1) {
            return false;
        }
        CardUtil.adjustCost((SpellAbility) abilityToModify, amount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && game.getCard(abilityToModify.getSourceId()) != null
                && abilityToModify.isControlledBy(source.getControllerId());
    }

    @Override
    public HeliodTheWarpedEclipseEffect copy() {
        return new HeliodTheWarpedEclipseEffect(this);
    }
}
