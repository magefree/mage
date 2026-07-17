package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MistmeadowCouncil extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterControlledPermanent(SubType.KITHKIN, "you control a Kithkin")
    );
    private static final Hint hint = new ConditionHint(condition, "You control a Kithkin");

    public MistmeadowCouncil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // This spell costs {1} less to cast if you control a Kithkin.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(1, condition));
        ability.setRuleAtTheTop(true);
        ability.addHint(hint);
        this.addAbility(ability);

        // When this creature enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private MistmeadowCouncil(final MistmeadowCouncil card) {
        super(card);
    }

    @Override
    public MistmeadowCouncil copy() {
        return new MistmeadowCouncil(this);
    }
}
