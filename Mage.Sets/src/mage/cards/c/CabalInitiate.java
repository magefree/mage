package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.AbilityWord;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class CabalInitiate extends CardImpl {

    public CabalInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Discard a card: Cabal Initiate gains lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn), new DiscardCardCost()));

        // Threshold — Cabal Initiate gets +1/+2 as long as seven or more cards are in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 2, Duration.WhileOnBattlefield),
                new CardsInControllerGraveyardCondition(7),
                "{this} gets +1/+2 as long as seven or more cards are in your graveyard"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private CabalInitiate(final CabalInitiate card) {
        super(card);
    }

    @Override
    public CabalInitiate copy() {
        return new CabalInitiate(this);
    }
}
