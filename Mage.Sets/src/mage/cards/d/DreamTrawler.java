package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamTrawler extends CardImpl {

    public DreamTrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you draw a card, Dream Trawler gets +1/+0 until end of turn.
        this.addAbility(new DrawCardControllerTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), false
        ));

        // Whenever Dream Trawler attacks, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // Discard a card: Dream Trawler gains hexproof until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ), new DiscardCardCost());
        ability.addEffect(new TapSourceEffect().setText("Tap it"));
        this.addAbility(ability);
    }

    private DreamTrawler(final DreamTrawler card) {
        super(card);
    }

    @Override
    public DreamTrawler copy() {
        return new DreamTrawler(this);
    }
}
