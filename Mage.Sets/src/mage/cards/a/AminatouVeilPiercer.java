package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MiracleGrantedAbility;
import mage.abilities.effects.common.cost.MiracleCostModifier;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

public final class AminatouVeilPiercer extends CardImpl {

    public AminatouVeilPiercer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, surveil 2.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SurveilEffect(2)));

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        this.addAbility(new MiracleGrantedAbility(StaticFilters.FILTER_CARD_ENCHANTMENTS,
            () -> new AminatouVeilPiercerEffect(),
            "its mana cost reduced by {4}"
        ));
    }

    private AminatouVeilPiercer(final AminatouVeilPiercer card) {
        super(card);
    }

    @Override
    public AminatouVeilPiercer copy() {
        return new AminatouVeilPiercer(this);
    }
}

class AminatouVeilPiercerEffect extends MiracleCostModifier {
    public AminatouVeilPiercerEffect() {
        super(Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    private AminatouVeilPiercerEffect(AminatouVeilPiercerEffect effect) {
        super(effect);
    }

    @Override
    public AminatouVeilPiercerEffect copy() {
        return new AminatouVeilPiercerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 4);
        return true;
    }
}
