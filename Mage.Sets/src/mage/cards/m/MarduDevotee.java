package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.effects.mana.AddManaFromColorChoicesEffect;
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
public final class MarduDevotee extends CardImpl {

    public MarduDevotee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When this creature enters, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // {1}: Add {R}, {W}, or {B}. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                new AddManaFromColorChoicesEffect(ManaType.RED, ManaType.WHITE, ManaType.BLACK), new GenericManaCost(1)
        ));
    }

    private MarduDevotee(final MarduDevotee card) {
        super(card);
    }

    @Override
    public MarduDevotee copy() {
        return new MarduDevotee(this);
    }
}
