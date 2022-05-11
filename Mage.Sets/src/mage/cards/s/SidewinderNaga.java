
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.DesertControlledOrGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class SidewinderNaga extends CardImpl {

    public SidewinderNaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // As long as  you control a Desert or there is a Desert card in your graveyard, Sidewinder Naga gets +1/+0 and has trample.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                DesertControlledOrGraveyardCondition.instance, "As long as  you control a Desert " +
                "or there is a Desert card in your graveyard, {this} gets +1/+0 "
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield
        ),DesertControlledOrGraveyardCondition.instance, "and has trample"));
        this.addAbility(ability.addHint(DesertControlledOrGraveyardCondition.getHint()));
    }

    private SidewinderNaga(final SidewinderNaga card) {
        super(card);
    }

    @Override
    public SidewinderNaga copy() {
        return new SidewinderNaga(this);
    }
}
