
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class WarfireJavelineer extends CardImpl {

    public WarfireJavelineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Warfire Javelineer enters the battlefield, it deals X damage to target creature an opponent controls, where X is the number of instant and sorcery cards in your graveyard.
        EntersBattlefieldTriggeredAbility ability =
                new EntersBattlefieldTriggeredAbility(
                        new DamageTargetEffect(
                                new CardsInControllerGraveyardCount(new FilterInstantOrSorceryCard()))
                                .setText("it deals X damage to target creature an opponent controls, where X is the number of instant and sorcery cards in your graveyard."));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private WarfireJavelineer(final WarfireJavelineer card) {
        super(card);
    }

    @Override
    public WarfireJavelineer copy() {
        return new WarfireJavelineer(this);
    }
}
