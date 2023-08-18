package mage.cards.r;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ControllerLostLifeCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RowanScionOfWar extends CardImpl {

    public RowanScionOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // {T}: Spells you cast this turn that are black and/or red cost {X} less to cast, where X is the amount of life you lost this turn. Activate only as a sorcery.
        this.addAbility(
                new ActivateAsSorceryActivatedAbility(
                        new RowanScionOfWarEffect(),
                        new TapSourceCost()
                ).addHint(ControllerLostLifeCount.getHint()),
                new PlayerLostLifeWatcher()
        );

    }

    private RowanScionOfWar(final RowanScionOfWar card) {
        super(card);
    }

    @Override
    public RowanScionOfWar copy() {
        return new RowanScionOfWar(this);
    }
}

class RowanScionOfWarEffect extends CostModificationEffectImpl {

    RowanScionOfWarEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Spells you cast this turn that are black and/or red cost {X} less to cast, where X is the amount of life you lost this turn.";
    }

    private RowanScionOfWarEffect(final RowanScionOfWarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, Math.max(0, ControllerLostLifeCount.instance.calculate(game, source, this)));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }
        SpellAbility spell = (SpellAbility) abilityToModify;
        ObjectColor color = spell.getCharacteristics(game).getColor(game);

        return spell.isControlledBy(source.getControllerId())
                && game.getCard(spell.getSourceId()) != null
                && (color.isRed() || color.isBlack());
    }

    @Override
    public RowanScionOfWarEffect copy() {
        return new RowanScionOfWarEffect(this);
    }
}
