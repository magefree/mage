package mage.cards.i;

import mage.MageInt;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Inkling11Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InformedInkwright extends CardImpl {

    public InformedInkwright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, create a 1/1 white and black Inkling creature token with flying.
        this.addAbility(new ReparteeAbility(new CreateTokenEffect(new Inkling11Token())));
    }

    private InformedInkwright(final InformedInkwright card) {
        super(card);
    }

    @Override
    public InformedInkwright copy() {
        return new InformedInkwright(this);
    }
}
