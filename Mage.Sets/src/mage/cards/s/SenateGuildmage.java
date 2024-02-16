package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class SenateGuildmage extends CardImpl {

    public SenateGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, {T}: You gain 2 life.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(2), new ManaCostsImpl<>("{W}"));
        ability1.addCost(new TapSourceCost());
        this.addAbility(ability1);

        // {U}, {T}: Draw a card, then discard a card.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), new ManaCostsImpl<>("{U}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);
    }

    private SenateGuildmage(final SenateGuildmage card) {
        super(card);
    }

    @Override
    public SenateGuildmage copy() {
        return new SenateGuildmage(this);
    }
}
