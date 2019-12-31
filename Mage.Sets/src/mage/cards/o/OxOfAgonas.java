package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.EscapesWithAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OxOfAgonas extends CardImpl {

    public OxOfAgonas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.OX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Ox of Agonas enters the battlefield, discard your hand, then draw three cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DiscardHandControllerEffect().setText("discard your hand,")
        );
        ability.addEffect(new DrawCardSourceControllerEffect(3).setText("then draw three cards"));
        this.addAbility(ability);

        // Escape—{R}{R}, Exile eight other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{R}{R}", 8));

        // Ox of Agonas escapes with a +1/+1 counter on it.
        this.addAbility(new EscapesWithAbility(1));
    }

    private OxOfAgonas(final OxOfAgonas card) {
        super(card);
    }

    @Override
    public OxOfAgonas copy() {
        return new OxOfAgonas(this);
    }
}
