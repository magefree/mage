package mage.cards.m;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MasteryOfTheUnseen extends CardImpl {

    public MasteryOfTheUnseen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever a permanent you control is turned face up, you gain 1 life for each creature you control.
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(
                new GainLifeEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE)),
                new FilterControlledPermanent("a permanent you control")));

        // {3}{W}: Manifest the top card of your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ManifestEffect(1), new ManaCostsImpl<>("{3}{W}")));
    }

    private MasteryOfTheUnseen(final MasteryOfTheUnseen card) {
        super(card);
    }

    @Override
    public MasteryOfTheUnseen copy() {
        return new MasteryOfTheUnseen(this);
    }
}
