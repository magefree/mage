
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class GeralfsMindcrusher extends CardImpl {

    public GeralfsMindcrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Geralf's Mindcrusher enters the battlefield, target player puts the top five cards of their library into their graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(5));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // Undying
        this.addAbility(new UndyingAbility());
    }

    private GeralfsMindcrusher(final GeralfsMindcrusher card) {
        super(card);
    }

    @Override
    public GeralfsMindcrusher copy() {
        return new GeralfsMindcrusher(this);
    }
}
