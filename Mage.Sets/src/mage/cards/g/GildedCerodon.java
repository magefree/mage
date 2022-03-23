package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DesertControlledOrGraveyardCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GildedCerodon extends CardImpl {

    private static final String rule = "Whenever {this} attacks, if you control a Desert or there is a Desert card in your graveyard, target creature can't block this turn.";

    public GildedCerodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Gilded Cerodon attacks, if you control a Desert or there is a Desert card in your graveyard, target creature can't block this turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), false),
                DesertControlledOrGraveyardCondition.instance, rule
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(DesertControlledOrGraveyardCondition.getHint()));

    }

    private GildedCerodon(final GildedCerodon card) {
        super(card);
    }

    @Override
    public GildedCerodon copy() {
        return new GildedCerodon(this);
    }
}
