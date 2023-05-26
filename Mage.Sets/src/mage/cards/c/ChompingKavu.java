package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChompingKavu extends CardImpl {

    public ChompingKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // This creature can't be blocked by creatures with power 2 or less.
        backupAbility.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(
                DauntAbility.getFilter(), Duration.WhileOnBattlefield
        ).setText("this creature can't be blocked by creatures with power 2 or less")));
    }

    private ChompingKavu(final ChompingKavu card) {
        super(card);
    }

    @Override
    public ChompingKavu copy() {
        return new ChompingKavu(this);
    }
}
