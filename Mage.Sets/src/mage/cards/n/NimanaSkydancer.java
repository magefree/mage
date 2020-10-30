package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NimanaSkydancer extends CardImpl {

    public NimanaSkydancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Nimana Skydancer enters the battlefield, target opponent mills two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private NimanaSkydancer(final NimanaSkydancer card) {
        super(card);
    }

    @Override
    public NimanaSkydancer copy() {
        return new NimanaSkydancer(this);
    }
}
