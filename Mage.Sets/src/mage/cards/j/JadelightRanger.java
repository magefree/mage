package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JadelightRanger extends CardImpl {

    public JadelightRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Jadelight Ranger enters the battlefield, it explores, then it explores again.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExploreSourceEffect(false, "it"), false);
        ability.addEffect(new ExploreSourceEffect().setText(", then it explores again. (Reveal the top card of your library. Put that card into your hand if it's a land. Otherwise, put a +1/+1 counter on this creature, then put the card back or put it into your graveyard. Then repeat this process.) "));
        this.addAbility(ability);
    }

    private JadelightRanger(final JadelightRanger card) {
        super(card);
    }

    @Override
    public JadelightRanger copy() {
        return new JadelightRanger(this);
    }
}
