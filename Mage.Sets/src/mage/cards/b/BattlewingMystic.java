package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class BattlewingMystic extends CardImpl {

    public BattlewingMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Battlewing Mystic enters the battlefield, if it was kicked, discard your hand, then draw two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardHandControllerEffect()).withInterveningIf(KickedCondition.ONCE);
        ability.addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
        this.addAbility(ability);
    }

    private BattlewingMystic(final BattlewingMystic card) {
        super(card);
    }

    @Override
    public BattlewingMystic copy() {
        return new BattlewingMystic(this);
    }
}
