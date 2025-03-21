package mage.cards.m;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.AddManaFromColorChoicesEffect;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ManaforgeCinder extends CardImpl {

    public ManaforgeCinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Add {B} or {R}. Activate this ability no more than three times each turn.
        AddManaFromColorChoicesEffect effect = new AddManaFromColorChoicesEffect(ManaType.BLACK, ManaType.RED);
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}"), 3, effect.getNetMana()
        ));
    }

    private ManaforgeCinder(final ManaforgeCinder card) {
        super(card);
    }

    @Override
    public ManaforgeCinder copy() {
        return new ManaforgeCinder(this);
    }
}
