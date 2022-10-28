package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzaPowerstoneProdigy extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("one or more artifact cards");

    public UrzaPowerstoneProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {1}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Whenever you discard one or more artifact cards, create a tapped Powerstone token. This ability triggers only once each turn.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new CreateTokenEffect(new PowerstoneToken(), 1, true), false, filter
        ).setTriggersOnce(true));
    }

    private UrzaPowerstoneProdigy(final UrzaPowerstoneProdigy card) {
        super(card);
    }

    @Override
    public UrzaPowerstoneProdigy copy() {
        return new UrzaPowerstoneProdigy(this);
    }
}
