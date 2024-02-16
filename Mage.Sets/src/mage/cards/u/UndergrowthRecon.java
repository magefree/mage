package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndergrowthRecon extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("land card from your graveyard");

    public UndergrowthRecon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // At the beginning of your upkeep, return target land card from your graveyard to the battlefield tapped.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private UndergrowthRecon(final UndergrowthRecon card) {
        super(card);
    }

    @Override
    public UndergrowthRecon copy() {
        return new UndergrowthRecon(this);
    }
}
