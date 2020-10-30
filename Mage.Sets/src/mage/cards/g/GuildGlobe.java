package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.AddManaOfTwoDifferentColorsEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuildGlobe extends CardImpl {

    public GuildGlobe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Guild Globe enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // {2}, {T}, Sacrifice Guild Globe: Add two mana of different colors.
        Ability ability = new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfTwoDifferentColorsEffect(), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private GuildGlobe(final GuildGlobe card) {
        super(card);
    }

    @Override
    public GuildGlobe copy() {
        return new GuildGlobe(this);
    }
}
