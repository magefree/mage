package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimJavelineer extends CardImpl {

    public GrimJavelineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you attack, target attacking creature gets +1/+0 until end of turn. When that creature dies this turn, surveil 1.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new BoostTargetEffect(1, 0), 1
        );
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new SurveilEffect(1))
        ));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private GrimJavelineer(final GrimJavelineer card) {
        super(card);
    }

    @Override
    public GrimJavelineer copy() {
        return new GrimJavelineer(this);
    }
}
