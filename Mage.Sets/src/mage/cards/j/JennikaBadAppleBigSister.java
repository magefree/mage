package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.RedMutantToken;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.PlainscyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class JennikaBadAppleBigSister extends CardImpl {

    public JennikaBadAppleBigSister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Jennika enters, create a 2/2 red Mutant creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RedMutantToken())));

        // Plainscycling {2}
        this.addAbility(new PlainscyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private JennikaBadAppleBigSister(final JennikaBadAppleBigSister card) {
        super(card);
    }

    @Override
    public JennikaBadAppleBigSister copy() {
        return new JennikaBadAppleBigSister(this);
    }
}
