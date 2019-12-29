package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrElenoraTheDiscerning extends CardImpl {

    public SyrElenoraTheDiscerning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Syr Elenora the Discerning's power is equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetPowerSourceEffect(CardsInControllerHandCount.instance, Duration.EndOfGame)
        ));

        // When Syr Elenora enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Spells your opponents cast that target Syr Elenora cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new SyrElenoraTheDiscerningCostIncreaseEffect()));
    }

    private SyrElenoraTheDiscerning(final SyrElenoraTheDiscerning card) {
        super(card);
    }

    @Override
    public SyrElenoraTheDiscerning copy() {
        return new SyrElenoraTheDiscerning(this);
    }
}

class SyrElenoraTheDiscerningCostIncreaseEffect extends CostModificationEffectImpl {

    SyrElenoraTheDiscerningCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target {this} cost {2} more to cast";
    }

    private SyrElenoraTheDiscerningCostIncreaseEffect(SyrElenoraTheDiscerningCostIncreaseEffect effect) {
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
    public SyrElenoraTheDiscerningCostIncreaseEffect copy() {
        return new SyrElenoraTheDiscerningCostIncreaseEffect(this);
    }

}
