package mage.cards.w;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WillScionOfPeace extends CardImpl {

    public WillScionOfPeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Spells you cast this turn that are white and/or blue cost {X} less to cast, where X is the amount of life you gained this turn. Activate only as a sorcery.
        this.addAbility(
                new ActivateAsSorceryActivatedAbility(
                        new WillScionOfPeaceEffect(),
                        new TapSourceCost()
                ).addHint(ControllerGainedLifeCount.getHint()),
                new PlayerGainedLifeWatcher()
        );
    }

    private WillScionOfPeace(final WillScionOfPeace card) {
        super(card);
    }

    @Override
    public WillScionOfPeace copy() {
        return new WillScionOfPeace(this);
    }
}

class WillScionOfPeaceEffect extends CostModificationEffectImpl {

    WillScionOfPeaceEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Spells you cast this turn that are white and/or blue cost {X} less to cast, where X is the amount of life you gained this turn.";
    }

    private WillScionOfPeaceEffect(final WillScionOfPeaceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, Math.max(0, ControllerGainedLifeCount.instance.calculate(game, source, this)));
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
                && (color.isBlue() || color.isWhite());
    }

    @Override
    public WillScionOfPeaceEffect copy() {
        return new WillScionOfPeaceEffect(this);
    }
}
