package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author NinthWorld
 */
public final class BorGullet extends CardImpl {

    public BorGullet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.CEPHALID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Bor Gullet enters the battlefield, target opponent reveals their hand. You choose a card from it. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardCardYouChooseTargetEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BorGullet(final BorGullet card) {
        super(card);
    }

    @Override
    public BorGullet copy() {
        return new BorGullet(this);
    }
}
