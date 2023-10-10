package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BilbosRing extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.HALFLING, "Halfling");

    public BilbosRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // As long as it's your turn, equipped creature has hexproof and can't be blocked.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(HexproofAbility.getInstance(), AttachmentType.EQUIPMENT),
                MyTurnCondition.instance, "as long as it's your turn, equipped creature has hexproof"
        ));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT),
                MyTurnCondition.instance, "and can't be blocked"
        ));
        this.addAbility(ability.addHint(MyTurnHint.instance));

        // Whenever equipped creature attacks alone, you draw a card and you lose 1 life.
        ability = new AttacksAloneAttachedTriggeredAbility(
                new DrawCardSourceControllerEffect(1, "you")
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // Equip Halfling {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), new TargetPermanent(filter)));

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private BilbosRing(final BilbosRing card) {
        super(card);
    }

    @Override
    public BilbosRing copy() {
        return new BilbosRing(this);
    }
}
