
package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.game.permanent.token.InsectToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfestedRoothold extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("artifacts");

    public InfestedRoothold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Protection from artifacts
        this.addAbility(new ProtectionAbility(filter));

        // Whenever an opponent casts an artifact spell, you may create a 1/1 green Insect creature token.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new CreateTokenEffect(new InsectToken()), StaticFilters.FILTER_SPELL_AN_ARTIFACT, true)
        );

    }

    private InfestedRoothold(final InfestedRoothold card) {
        super(card);
    }

    @Override
    public InfestedRoothold copy() {
        return new InfestedRoothold(this);
    }
}
