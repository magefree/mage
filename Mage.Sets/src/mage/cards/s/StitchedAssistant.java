package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StitchedAssistant extends CardImpl {

    public StitchedAssistant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Stitched Assistant exploits a creature, scry 1, then draw a card.
        Ability ability = new ExploitCreatureTriggeredAbility(new ScryEffect(1, false));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);
    }

    private StitchedAssistant(final StitchedAssistant card) {
        super(card);
    }

    @Override
    public StitchedAssistant copy() {
        return new StitchedAssistant(this);
    }
}
