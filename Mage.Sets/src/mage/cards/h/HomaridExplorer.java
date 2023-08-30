package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

public final class HomaridExplorer extends CardImpl {

    public HomaridExplorer(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        subtype.add(SubType.HOMARID, SubType.SCOUT);
        power = new MageInt(3);
        toughness = new MageInt(3);

        // When Homarid Explorer enters the battlefield, target player puts the top four cards of their library into their graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(4));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public HomaridExplorer(final HomaridExplorer homaridExplorer) {
        super(homaridExplorer);
    }

    @Override
    public HomaridExplorer copy() {
        return new HomaridExplorer(this);
    }

}
