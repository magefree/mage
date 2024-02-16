package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GogglesOfNight extends CardImpl {

    public GogglesOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage to a player, scry 1, then draw a card.
        Ability ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new ScryEffect(1, false), "equipped", false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private GogglesOfNight(final GogglesOfNight card) {
        super(card);
    }

    @Override
    public GogglesOfNight copy() {
        return new GogglesOfNight(this);
    }
}
