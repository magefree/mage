
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author TheElk801
 */
public final class InfestedRoothold extends CardImpl {

    private final static FilterSpell filter = new FilterSpell("an artifact spell");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public InfestedRoothold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Protection from artifacts
        this.addAbility(new ProtectionAbility(new FilterArtifactCard("artifacts")));

        // Whenever an opponent casts an artifact spell, you may create a 1/1 green Insect creature token.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new CreateTokenEffect(new InsectToken()), filter, true));

    }

    public InfestedRoothold(final InfestedRoothold card) {
        super(card);
    }

    @Override
    public InfestedRoothold copy() {
        return new InfestedRoothold(this);
    }
}
