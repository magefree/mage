package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author muz
 */
public final class KlawSonicSubjugator extends CardImpl {

    public KlawSonicSubjugator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sonic Attack -- When Klaw enters, target player reveals a number of cards from their hand equal to one plus the number of creature cards in your graveyard. You choose one of them. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new DiscardCardYouChooseTargetEffect(new IntPlusDynamicValue(
                1, new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
            ))
        ).withFlavorWord("Sonic Attack");
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private KlawSonicSubjugator(final KlawSonicSubjugator card) {
        super(card);
    }

    @Override
    public KlawSonicSubjugator copy() {
        return new KlawSonicSubjugator(this);
    }
}
