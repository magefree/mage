package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IgneousElemental extends CardImpl {

    public IgneousElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // This spell costs {2} less to cast if there is a land card in your graveyard.
        Condition condition = new CardsInControllerGraveyardCondition(1, StaticFilters.FILTER_CARD_LAND);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2, condition)
                .setText("This spell costs {2} less to cast if there is a land card in your graveyard."));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ConditionHint(condition, "There is a land card in your graveyard"));
        this.addAbility(ability);

        // When Igneous Elemental enters the battlefield, you may have it deal 2 damage to target creature.
        ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(2)
                        .setText("have it deal 2 damage to target creature"),
                true
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private IgneousElemental(final IgneousElemental card) {
        super(card);
    }

    @Override
    public IgneousElemental copy() {
        return new IgneousElemental(this);
    }
}
