package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerOrBattleTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RooftopSaboteurs extends CardImpl {

    public RooftopSaboteurs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setBlue(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Rooftop Saboteurs deals combat damage to a player or battle, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerOrBattleTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false));
    }

    private RooftopSaboteurs(final RooftopSaboteurs card) {
        super(card);
    }

    @Override
    public RooftopSaboteurs copy() {
        return new RooftopSaboteurs(this);
    }
}
