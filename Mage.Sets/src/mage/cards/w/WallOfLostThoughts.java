package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WallOfLostThoughts extends CardImpl {

    public WallOfLostThoughts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Wall of Lost Thoughts enters the battlefield, target player puts the top four cards of their library into their graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(4));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private WallOfLostThoughts(final WallOfLostThoughts card) {
        super(card);
    }

    @Override
    public WallOfLostThoughts copy() {
        return new WallOfLostThoughts(this);
    }
}
