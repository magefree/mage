package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.AddManaFromColorChoicesEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SultaiDevotee extends CardImpl {

    public SultaiDevotee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {1}: Add {B}, {G}, or {U}. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                new AddManaFromColorChoicesEffect(ManaType.BLACK, ManaType.GREEN, ManaType.BLUE), new GenericManaCost(1)
        ));
    }

    private SultaiDevotee(final SultaiDevotee card) {
        super(card);
    }

    @Override
    public SultaiDevotee copy() {
        return new SultaiDevotee(this);
    }
}
