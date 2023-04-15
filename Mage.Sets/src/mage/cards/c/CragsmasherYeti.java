package mage.cards.c;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.MountaincyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CragsmasherYeti extends CardImpl {

    public CragsmasherYeti(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.YETI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Mountaincycling {2}
        this.addAbility(new MountaincyclingAbility(new ManaCostsImpl<>("{2}")));

        // Backup 2
        BackupAbility backupAbility = new BackupAbility(this, 2);
        this.addAbility(backupAbility);

        // Trample
        backupAbility.addAbility(TrampleAbility.getInstance());
    }

    private CragsmasherYeti(final CragsmasherYeti card) {
        super(card);
    }

    @Override
    public CragsmasherYeti copy() {
        return new CragsmasherYeti(this);
    }
}
