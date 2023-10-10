package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerOrBattleTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoomskarWarrior extends CardImpl {

    public DoomskarWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Trample
        backupAbility.addAbility(TrampleAbility.getInstance());

        // Whenever this creature deals combat damage to a player or battle, look at that many cards from the top of your library. You may reveal a creature or land card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        backupAbility.addAbility(new DealsCombatDamageToAPlayerOrBattleTriggeredAbility(
                new LookLibraryAndPickControllerEffect(
                        SavedDamageValue.MANY, 1,
                        StaticFilters.FILTER_CARD_CREATURE_OR_LAND,
                        PutCards.HAND, PutCards.BOTTOM_RANDOM
                ), false
        ).setTriggerPhrase("Whenever this creature deals combat damage to a player or battle, "));
    }

    private DoomskarWarrior(final DoomskarWarrior card) {
        super(card);
    }

    @Override
    public DoomskarWarrior copy() {
        return new DoomskarWarrior(this);
    }
}
