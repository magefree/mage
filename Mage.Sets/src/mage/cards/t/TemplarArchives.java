package mage.cards.t;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author NinthWorld
 */
public final class TemplarArchives extends CardImpl {

    public TemplarArchives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Templar Archives enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Templar Archives enters the battlefield, draw a card, then discard a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new DiscardControllerEffect(1, false));
        this.addAbility(ability);

        // {T}: Add {U} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new TapSourceCost()));
    }

    public TemplarArchives(final TemplarArchives card) {
        super(card);
    }

    @Override
    public TemplarArchives copy() {
        return new TemplarArchives(this);
    }
}
