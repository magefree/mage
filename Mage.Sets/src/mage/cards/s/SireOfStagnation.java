
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class SireOfStagnation extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();
    private static final String rule = "Whenever a land enters the battlefield under an opponent's control, that player exiles the top two cards of their library and you draw two cards.";

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SireOfStagnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Devoid <i>(This card has no color.)</i>
        this.addAbility(new DevoidAbility(this.color));

        // Whenever a land enters the battlefield under an opponent's control, that player exiles the top two cards of their library and you draw two cards.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                new ExileCardsFromTopOfLibraryTargetEffect(2, "that player"), filter, false, SetTargetPointer.PLAYER, rule, false);
        ability.addEffect(new DrawCardSourceControllerEffect(2));
        this.addAbility(ability);
    }

    private SireOfStagnation(final SireOfStagnation card) {
        super(card);
    }

    @Override
    public SireOfStagnation copy() {
        return new SireOfStagnation(this);
    }
}
