package mage.cards.q;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.BlackWizardToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QueenBrahne extends CardImpl {

    public QueenBrahne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever Queen Brahne attacks, create a 0/1 black Wizard creature token with "Whenever you cast a noncreature spell, this token deals 1 damage to each opponent."
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new BlackWizardToken())));
    }

    private QueenBrahne(final QueenBrahne card) {
        super(card);
    }

    @Override
    public QueenBrahne copy() {
        return new QueenBrahne(this);
    }
}
